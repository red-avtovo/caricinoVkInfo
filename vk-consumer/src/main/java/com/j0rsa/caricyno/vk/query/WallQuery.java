package com.j0rsa.caricyno.vk.query;

import com.vk.api.sdk.queries.wall.WallGetQuery;

import static com.j0rsa.caricyno.vk.AppConfig.vk;
import static com.j0rsa.caricyno.vk.actor.ServiceActorData.service;

public class WallQuery {
    private WallGetQuery wallGetQuery;

    private WallQuery() {
        wallGetQuery = vk().wall().get(service().actor());
    }

    public static WallQuery wall() {
        return new WallQuery();
    }

    public WallGetQuery query() {
        return wallGetQuery;
    }

}
