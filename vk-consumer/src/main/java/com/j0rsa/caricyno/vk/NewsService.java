package com.j0rsa.caricyno.vk;

import com.j0rsa.caricyno.vk.news.WallNews;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
    private WallNews wallNews;

    public GetResponse getData() throws ClientException, ApiException {
        return wallNews.getLastOwnerRecord();
    }
}
