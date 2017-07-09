package com.j0rsa.caricyno.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {
    private static VkApiClient vk;
    private static String accessToken;
    @Autowired
    private VkProperties vkProperties;

    public static VkApiClient vk() {
        return vk;
    }

    public static String accessToken() {
        return accessToken;
    }


    @PostConstruct
    public void init() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
//        oauth();
    }

    private void oauth() {
        try {
            UserAuthResponse authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(vkProperties.getClient(), vkProperties.getSecret(), vkProperties.getHost(), vkProperties.getCode())
                    .execute();
            accessToken = authResponse.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
