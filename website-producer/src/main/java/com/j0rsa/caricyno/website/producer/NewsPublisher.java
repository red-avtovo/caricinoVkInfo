package com.j0rsa.caricyno.website.producer;

import com.j0rsa.caricyno.website.producer.utils.SSLExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author red
 * @since 07.05.17
 */

@Service
public class NewsPublisher {
    private final WebsiteProperties properties;
    private final AuthorizationModule authorizationModule;
    private final String articleAddUrl;

    public static final ContentType TEXT_PLAIN = ContentType.create("text/plain", Consts.UTF_8);
    private Cookie authCookie;
    private Function<String, String> emptyOrValue = value -> value == null ? "" : value;

    @Autowired
    public NewsPublisher(WebsiteProperties properties, AuthorizationModule authorizationModule) {
        this.properties = properties;
        this.authorizationModule = authorizationModule;
        this.articleAddUrl = properties.getUrl() + "/articles/add";
    }

    public boolean publish(NewsObject newsObject) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        authorizeIfNotAuthorized();

        final Request request = Request.Post(articleAddUrl)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Origin", properties.getUrl())
                .addHeader("Referer", articleAddUrl)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .body(makeFormParams(newsObject));
        Executor executor = SSLExecutor.getExecutor();
        final BasicCookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(authCookie);
        final HttpResponse httpResponse = executor.use(cookieStore)
                .execute(request)
                .returnResponse();
        final String responseContent = StreamUtils.copyToString(httpResponse.getEntity().getContent(), Charset.defaultCharset());

        final String debugProperty = System.getProperty("com.j0rsa.caricyno.website.producer.NewsPublisher.DEBUG");
        if (emptyOrValue.apply(debugProperty).equals("true")) {
            System.out.println(String.format(
                    "-------------------------------\n\n" +
                            "%s" +
                            "\n\n-----------------------------",
                    responseContent));
        }
        return doesntContainErrorsInHtml(responseContent);
    }

    private boolean doesntContainErrorsInHtml(String responseContent) {
        return responseContent.contains("Article has been added.")
                &&
                !responseContent.contains("form-errors");
    }

    private void authorizeIfNotAuthorized() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if (authCookie == null || !authorizationModule.isAuthorized(authCookie)) {
            authCookie = authorizationModule.authorize();
        }
    }

    private HttpEntity makeFormParams(NewsObject newsObject) {
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addTextBody("title", newsObject.getTitle(), TEXT_PLAIN)
                .addTextBody("category_id", String.valueOf(newsObject.getCategory().getCategoryId()), TEXT_PLAIN)
                .addTextBody("MAX_FILE_SIZE", "268435456", TEXT_PLAIN)
                .addTextBody("description", newsObject.getHtmlText(), TEXT_PLAIN)
                .addTextBody("search", newsObject.isVisibleInSearchEngines() ? "true" : "", TEXT_PLAIN)
                .addTextBody("tags_info", String.join(", ", newsObject.getTags()), TEXT_PLAIN)
                .addTextBody("auth_view", newsObject.getVisibility().toString().toLowerCase(), TEXT_PLAIN)
                .addTextBody("auth_comment", newsObject.getCommentsRights().toString().toLowerCase(), TEXT_PLAIN);

        if (newsObject.getMainPhoto() != null) {
            final ContentType contentType = getContentType(newsObject.getMainPhoto());
            final String extension = contentType.getMimeType().split("/")[1];
            final FileBody fileBody = new FileBody(
                    downloadImageToTmp(newsObject.getMainPhoto()),
                    contentType,
                    "tempFile." + extension);
            builder.addPart("art_photo", fileBody);
        } else {
            builder.addBinaryBody("art_photo",
                    new byte[0],
                    ContentType.APPLICATION_OCTET_STREAM,
                    "");
        }

        return builder.build();
    }

    private ContentType getContentType(URL mainPhoto) {
        if (mainPhoto != null) {
            try {
                Header[] contentTypeHeader = getContentTypeHeadHeaders(mainPhoto);
                if (contentTypeHeader.length == 0) contentTypeHeader = getContentTypeGetHeaders(mainPhoto);
                final String contentType = contentTypeHeader[0].getValue();
                return ContentType.create(contentType, Consts.UTF_8);
            } catch (IOException e) {
                return ContentType.create(MimeTypeUtils.IMAGE_JPEG.toString());
            }
        } else {
            return ContentType.create(MimeTypeUtils.IMAGE_JPEG.toString());
        }
    }

    private Header[] getContentTypeHeadHeaders(URL mainPhoto) throws IOException {
        return Request.Head(String.valueOf(mainPhoto))
                .execute()
                .returnResponse()
                .getHeaders("Content-Type");
    }

    private Header[] getContentTypeGetHeaders(URL mainPhoto) throws IOException {
        return Request.Get(String.valueOf(mainPhoto))
                .execute()
                .returnResponse()
                .getHeaders("Content-Type");
    }

    private File downloadImageToTmp(URL mainPhoto) {
        final File tempFile = new File("/tmp/" + UUID.randomUUID());
        if (mainPhoto != null) {
            try {
                FileUtils.copyURLToFile(mainPhoto, tempFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                tempFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }
}
