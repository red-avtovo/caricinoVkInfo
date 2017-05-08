package com.j0rsa.caricyno.vk;

import com.j0rsa.caricyno.vk.news.WallNews;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

public class AppConfig {
    public static Integer GROUP_ID = -1;
    public static Integer CLIENT_ID = 1;
    public static String CLIENT_SECRET = "secret";
    public static String SERVICE_KEY = "service";
    public static GetResponse lastResponse;
    private static VkApiClient vk;

    public static void main(String[] args) throws Exception {
        init();
        getData();
    }

    private static void getData() throws ClientException, ApiException {
        WallNews wallNews = new WallNews();
        lastResponse = wallNews.getLastOwnerRecord();
    }

    private static void init() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
    }

    public static VkApiClient vk() {
        return vk;
    }
}
