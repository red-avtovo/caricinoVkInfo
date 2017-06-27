package com.j0rsa.caricyno.vk.query;

import com.j0rsa.caricyno.vk.actor.ServiceActorData;
import com.vk.api.sdk.queries.photos.PhotosGetQuery;

import static com.j0rsa.caricyno.vk.AppConfig.vk;

public class PhotoQuery {
    private PhotosGetQuery photosGetQuery;

    private PhotoQuery(ServiceActorData service) {
        this.photosGetQuery = vk().photos().get(service.actor());
    }

    public static PhotoQuery photo(ServiceActorData service) {
        return new PhotoQuery(service);
    }

    public PhotosGetQuery query() {
        return photosGetQuery;
    }
}
