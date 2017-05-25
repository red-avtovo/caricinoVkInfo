package com.j0rsa.caricyno.vk.news;

import com.vk.api.sdk.objects.wall.responses.GetResponse;

abstract class News {
    abstract GetResponse getLastOwnerRecord() throws Exception;

    public abstract GetResponse findLastPosts(Integer count) throws Exception;
}
