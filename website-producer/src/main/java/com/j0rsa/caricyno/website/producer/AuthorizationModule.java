package com.j0rsa.caricyno.website.producer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author red
 * @since 21.05.17
 */

@Service
public class AuthorizationModule {

    private final WebsiteProperties properties;

    private final Predicate<String> propertyStartsFromPhpSessId = value -> value.startsWith("PHPSESSID");
    private final Predicate<? super Header> isSetCookieHeader = header ->
            header.getName().toLowerCase().equals("set-cookie");
    private final Predicate<? super Header> isHeaderValueStartsFromPhpSessId =
            header -> header.getValue().toUpperCase().startsWith("PHPSESSID");
    private final Function<String, String> getPropertyValue =
            value -> value.split("=")[1];

    private final CloseableHttpClient closeableHttpClient;
    private final Request authRequest;
    private final Request authCheckRequest;

    @Autowired
    public AuthorizationModule(WebsiteProperties properties) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.properties = properties;
        String authCheckUrl = properties.getUrl() + "/activity/notifications/update";
        String authUrl = properties.getUrl() + "/login";
        authRequest = Request.Post(authUrl)
                .bodyForm(
                        Form.form()
                                .add("wEMlxxkq2D", properties.getUsername())
                                .add("password", properties.getPassword())
                                .add("email_field", "d0VNbHh4a3EyRA==")
                                .build()
                );
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (certificate, authType) -> true).build();
        closeableHttpClient = HttpClients
                .custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();
        authCheckRequest = Request.Post(authCheckUrl);
    }

    public String authorize() throws IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        final String phpsessid = getConnectedClientSessionId();
        final Executor executor = getExecutorWithPhpSessionCookie(phpsessid);
        executor.execute(authRequest).discardContent();
        return phpsessid;
    }

    private String getConnectedClientSessionId() throws IOException {
        final CloseableHttpResponse sessionResponse =
                closeableHttpClient.execute(new HttpGet(properties.getUrl()));
        return getPhpSessIdFromResponse(sessionResponse);
    }

    private String getPhpSessIdFromResponse(CloseableHttpResponse sessionResponse) throws IllegalStateException {
        return Arrays.stream(sessionResponse.getAllHeaders())
                .filter(isSetCookieHeader)
                .filter(isHeaderValueStartsFromPhpSessId)
                .findFirst()
                .map(header -> {
                    final String[] headerValueInfo = header.getValue().split(";");
                    return Arrays.stream(headerValueInfo)
                            .filter(propertyStartsFromPhpSessId)
                            .findFirst()
                            .map(getPropertyValue)
                            .orElse(null);
                })
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean isAuthorized(String authCookieString) throws IOException {
        final Executor executorWithPhpSessionCookie = getExecutorWithPhpSessionCookie(authCookieString);
        final HttpResponse authResponse = executorWithPhpSessionCookie
                .execute(authCheckRequest)
                .returnResponse();
        final int statusCode = authResponse
                .getStatusLine()
                .getStatusCode();
        if (statusCode == 200) return true;
        if (statusCode == 403) return false;
        throw new IllegalStateException("Unexpected return code: " + statusCode);
    }

    private Executor getExecutorWithPhpSessionCookie(String authCookieString) {
        final Executor executor = Executor.newInstance(closeableHttpClient);
        final BasicCookieStore cookieStore = initCookieStoreWithPhpSessId(authCookieString);
        return executor.use(cookieStore);
    }

    private BasicCookieStore initCookieStoreWithPhpSessId(String authCookieString) {
        final BasicCookieStore cookieStore = new BasicCookieStore();
        final BasicClientCookie phpsessid = getPhpSessIdCookie(authCookieString);
        cookieStore.addCookie(phpsessid);
        return cookieStore;
    }

    private BasicClientCookie getPhpSessIdCookie(String authCookieString) {
        final BasicClientCookie phpsessid = new BasicClientCookie("PHPSESSID", authCookieString);
        phpsessid.setDomain(properties.getDomain());
        phpsessid.setPath("/");
        return phpsessid;
    }
}
