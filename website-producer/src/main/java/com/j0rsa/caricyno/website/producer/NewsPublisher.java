package com.j0rsa.caricyno.website.producer;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author red
 * @since 07.05.17
 */

@Service
public class NewsPublisher {
    private final WebsiteProperties properties;
    private final AuthorizationModule authorizationModule;
    private final String articleAddUrl;

    private String authCookie = "";

    @Autowired
    public NewsPublisher(WebsiteProperties properties, AuthorizationModule authorizationModule) {
        this.properties = properties;
        this.authorizationModule = authorizationModule;
        this.articleAddUrl = properties.getUrl() + "/articles/add";
    }

    public boolean publish(NewsObject newsObject) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        authorizeIfNotAuthorized();
        final List<NameValuePair> formParams = makeFormParams(newsObject);
        final Request request = Request.Post(articleAddUrl)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Origin", properties.getUrl())
                .addHeader("Referer", articleAddUrl)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .bodyForm(formParams);
        Executor executor = Executor.newInstance();
        final BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie phpsessid = new BasicClientCookie("PHPSESSID", authCookie);
        phpsessid.setDomain(properties.getDomain());
        phpsessid.setPath("/");
        cookieStore.addCookie(phpsessid);
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

    private ArrayList<NameValuePair> makeFormParams(NewsObject newsObject) {
        ArrayList<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("title", newsObject.getTitle()));
        formParams.add(new BasicNameValuePair("category_id",
                String.valueOf(newsObject.getCategory().getCategoryId())
                ));
        formParams.add(new BasicNameValuePair("MAX_FILE_SIZE", "268435456"));
        formParams.add(new BasicNameValuePair("art_photo", ""));
        formParams.add(new BasicNameValuePair("description", newsObject.getHtmlText()));
        formParams.add(new BasicNameValuePair("search", newsObject.isVisibleInSearchEngines()?"true":""));
        formParams.add(new BasicNameValuePair("tags_info", String.join(", ", newsObject.getTags())));
        formParams.add(new BasicNameValuePair("auth_view",
                newsObject.getVisibility().toString().toLowerCase())
        );
        formParams.add(new BasicNameValuePair("auth_comment",
                newsObject.getCommentsRights().toString().toLowerCase())
        );

        return formParams;
    }
}
