package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.vk.NewsService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.j0rsa.caricyno.website.producer.NewsPublisher;
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

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class PostController {
    private final NewsService newsService;
    private final ConversionService conversionService;
    private final NewsPublisher newsPublisher;

    @Autowired
    public PostController(NewsService newsService, ConversionService conversionService, NewsPublisher newsPublisher) {
        this.newsService = newsService;
        this.conversionService = conversionService;
        this.newsPublisher = newsPublisher;
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

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public NewsObject create(Post post) {
        return conversionService.convert(post, NewsObject.class);
    }

    @RequestMapping(value = "posts/save", method = RequestMethod.POST)
    public void save(NewsObject newsObject) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        newsPublisher.publish(newsObject);
    }
}
