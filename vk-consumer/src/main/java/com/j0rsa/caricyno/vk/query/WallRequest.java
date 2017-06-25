package com.j0rsa.caricyno.vk.query;

import com.j0rsa.caricyno.vk.actor.ServiceActorData;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.wall.WallGetFilter;
import com.vk.api.sdk.queries.wall.WallGetQuery;
import lombok.Builder;

import static com.j0rsa.caricyno.vk.query.WallQuery.wall;

@Builder
public class WallRequest {
    private ServiceActorData service;
    private WallGetFilter filter;
    private Integer count;
    private Integer owner;

    public GetResponse execute() throws ClientException, ApiException {
        WallGetQuery query = query();
        query = ifFilterExistThenAddFilter(query);
        return query.execute();
    }

    private WallGetQuery ifFilterExistThenAddFilter(WallGetQuery query) {
        if (filter != null) {
            query = query.filter(filter);
        }
        return query;
    }

    private WallGetQuery query() {
        return wall(service).query()
                .ownerId(owner)
                .count(count);
    }

}
