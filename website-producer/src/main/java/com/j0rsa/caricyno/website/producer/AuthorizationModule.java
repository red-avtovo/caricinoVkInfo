package com.j0rsa.caricyno.website.producer;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author red
 * @since 21.05.17
 */

@Service
public class AuthorizationModule {

    private final WebsiteProperties properties;
    private final String authCheckUrl;
    private final String authUrl;

    @Autowired
    public AuthorizationModule(WebsiteProperties properties) {
        this.properties = properties;
        this.authCheckUrl = properties.getUrl() + "/activity/notifications/update";
        this.authUrl = properties.getUrl() + "/login";
    }

    public String authorize() throws IOException {
        final Response response = Request.Post(authUrl)
                .bodyForm(
                        Form.form()
                                .add("email", properties.getUsername())
                                .add("password", properties.getPassword())
                                .build()
                ).execute();
        return response.toString();
    }

    public boolean isAuthorized(String authCookie) throws IOException {
        final Request request = Request.Post(authCheckUrl)
                .addHeader("Accept", "application/json")
                .addHeader("Origin", properties.getUrl())
                .addHeader("Referer", authCheckUrl)
                .addHeader("X-Request", "JSON")
                .addHeader("X-Requested-With", "XMLHttpRequest");
        Executor executor = Executor.newInstance();
        final BasicCookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(new BasicClientCookie("PHPSESSID", authCookie));
        return executor.use(cookieStore)
                .execute(request)
                .returnResponse()
                .getStatusLine()
                .getStatusCode() == 200;
    }
}
