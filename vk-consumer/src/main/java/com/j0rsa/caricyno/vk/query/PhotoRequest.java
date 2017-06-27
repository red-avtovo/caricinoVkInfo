package com.j0rsa.caricyno.vk.query;

import com.j0rsa.caricyno.vk.actor.ServiceActorData;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;
import lombok.Builder;

import static com.j0rsa.caricyno.vk.query.PhotoQuery.photo;

@Builder
public class PhotoRequest {
    private ServiceActorData service;
    private String albumId;
    private Integer owner;

    public GetResponse execute() throws ClientException, ApiException {
        PhotosGetQuery query = query();
        return query.execute();
    }

    private PhotosGetQuery query() {
        return photo(service).query()
                .ownerId(owner)
                .albumId(albumId);
    }
}
