package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.vk.NewsService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallpostFull;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final NewsService newsService;
    private final ConversionService conversionService;

    @Autowired
    public PostController(NewsService newsService, ConversionService conversionService) {
        this.newsService = newsService;
        this.conversionService = conversionService;
    }

    GetResponse getNews() throws ClientException, ApiException {
        return newsService.getData();
    }


    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> findLastNPosts(Integer count) throws ClientException, ApiException {
        GetResponse response = newsService.findLastPosts(count);
        List<Post> posts = Lists.newArrayList();
        for (WallpostFull wallpostFull : response.getItems()) {
            posts.add(conversionService.convert(wallpostFull, Post.class));
        }
        return posts;
    }
}
