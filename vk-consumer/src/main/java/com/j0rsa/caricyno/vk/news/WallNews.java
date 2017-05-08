package com.j0rsa.caricyno.vk.news;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import static com.j0rsa.caricyno.vk.AppConfig.GROUP_ID;
import static com.j0rsa.caricyno.vk.query.WallQuery.wall;
import static com.vk.api.sdk.queries.wall.WallGetFilter.OWNER;

public class WallNews extends News {

    public GetResponse getLastOwnerRecord() throws ClientException, ApiException {
        return wall().query()
                .ownerId(GROUP_ID)
                .count(1)
                .filter(OWNER)
                .execute();
    }
}
