package com.j0rsa.caricyno.vk.query;

import com.j0rsa.caricyno.vk.actor.ServiceActorData;
import com.vk.api.sdk.queries.wall.WallGetQuery;

import static com.j0rsa.caricyno.vk.AppConfig.vk;

public class WallQuery {
    private WallGetQuery wallGetQuery;

    private WallQuery(ServiceActorData service) {
        wallGetQuery = vk().wall().get(service.actor());
    }

    public static WallQuery wall(ServiceActorData service) {
        return new WallQuery(service);
    }

    public WallGetQuery query() {
        return wallGetQuery;
    }

}
