package com.j0rsa.caricyno.website.producer.utils;

import org.apache.http.client.fluent.Executor;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;

/**
 * @author red
 * @since 26.05.17
 */

public class SSLExecutor {

    private static CloseableHttpClient closeableHttpClient =
            HttpClients
                    .custom()
                    .setSSLContext(getSslContext())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();

    public static Executor getExecutor() {
        return Executor.newInstance(closeableHttpClient);
    }

    private static SSLContext getSslContext() {
        try {
            return new SSLContextBuilder()
                    .loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
