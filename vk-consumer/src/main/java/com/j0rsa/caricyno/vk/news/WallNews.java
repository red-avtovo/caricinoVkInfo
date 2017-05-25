package com.j0rsa.caricyno.vk.news;

import com.j0rsa.caricyno.vk.VkProperties;
import com.j0rsa.caricyno.vk.query.WallRequest;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.j0rsa.caricyno.vk.actor.ServiceActorData.service;
import static com.vk.api.sdk.queries.wall.WallGetFilter.OWNER;

@Service
public class WallNews extends News {
    private final VkProperties vkProperties;

    @Autowired
    public WallNews(VkProperties vkProperties) {
        this.vkProperties = vkProperties;
    }

    public GetResponse getLastOwnerRecord() throws ClientException, ApiException {
        return ownersWallPosts()
                .count(1)
                .build()
                .execute();
    }

    private WallRequest.WallRequestBuilder ownersWallPosts() {
        return WallRequest.builder()
                .service(service(vkProperties))
                .owner(vkProperties.getGroup())
                .filter(OWNER);
    }

    @Override
    public GetResponse findLastPosts(Integer count) throws ClientException, ApiException {
        return ownersWallPosts()
                .count(count)
                .build()
                .execute();
    }
}
