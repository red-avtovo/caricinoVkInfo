package com.j0rsa.caricyno.website.producer;

import com.j0rsa.caricyno.website.producer.utils.SSLExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
    private Function<URL, Boolean> isEmptyOrNull = value -> value == null || value.toString().isEmpty();

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
        return executor.use(cookieStore)
                .execute(request)
                .returnResponse()
                .getStatusLine()
                .getStatusCode() == 200;
    }

    private void authorizeIfNotAuthorized() throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        if (authCookie == null || !authorizationModule.isAuthorized(authCookie)) {
            authCookie = authorizationModule.authorize();
        }
    }

    private HttpEntity makeFormParams(NewsObject newsObject) {
        byte[] nullImage = new byte[0];
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("title", newsObject.getTitle(), TEXT_PLAIN)
                .addTextBody("category_id", String.valueOf(newsObject.getCategory().getCategoryId()), TEXT_PLAIN)
                .addTextBody("MAX_FILE_SIZE", "268435456", TEXT_PLAIN)
                .addBinaryBody("art_photo",
                        isEmptyOrNull.apply(newsObject.getMainPhoto()) ? nullImage :
                                downloadImageToTmp(newsObject.getMainPhoto()),
                        ContentType.APPLICATION_OCTET_STREAM,
                        isEmptyOrNull.apply(newsObject.getMainPhoto()) ? "" : "tempFile")
                .addTextBody("description", newsObject.getHtmlText(), TEXT_PLAIN)
                .addTextBody("search", newsObject.isVisibleInSearchEngines() ? "true" : "", TEXT_PLAIN)
                .addTextBody("tags_info", String.join(", ", newsObject.getTags()), TEXT_PLAIN)
                .addTextBody("auth_view", newsObject.getVisibility().toString().toLowerCase(), TEXT_PLAIN)
                .addTextBody("auth_comment", newsObject.getCommentsRights().toString().toLowerCase(), TEXT_PLAIN);
        return builder.build();
    }

    private byte[] downloadImageToTmp(URL mainPhoto) {
        try {
            final File tempFile = new File("/tmp/" + UUID.randomUUID());
            FileUtils.copyURLToFile(mainPhoto, tempFile);
            final byte[] imageBytes = Files.readAllBytes(tempFile.toPath());
            if (!tempFile.delete()) {
                tempFile.deleteOnExit();
            }
            return imageBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
