package com.j0rsa.caricyno.vk;

import com.j0rsa.caricyno.vk.query.PhotoRequest;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.j0rsa.caricyno.vk.actor.ServiceActorData.service;

@Service
public class PhotoAlbumService {
    private final VkProperties vkProperties;

    @Autowired
    public PhotoAlbumService(VkProperties vkProperties) {
        this.vkProperties = vkProperties;
    }

    public GetResponse findPhotos(Integer ownerId, Integer albumId) throws ClientException, ApiException {
        return PhotoRequest.builder()
                .service(service(vkProperties))
                .owner(ownerId)
                .albumId(String.valueOf(albumId))
                .build()
                .execute();
    }
}
