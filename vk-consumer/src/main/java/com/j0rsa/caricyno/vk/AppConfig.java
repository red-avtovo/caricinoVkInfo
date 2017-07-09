package com.j0rsa.caricyno.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {
    private static VkApiClient vk;

    public static VkApiClient vk() {
        return vk;
    }


    @PostConstruct
    public void init() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
    }
}
