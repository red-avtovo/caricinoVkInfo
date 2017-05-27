package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.application.services.PostsService;
import com.j0rsa.caricyno.vk.NewsService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.j0rsa.caricyno.website.producer.NewsPublisher;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PostsService postsService;
    private final NewsPublisher newsPublisher;

    @Autowired
    public PostController(NewsService newsService, PostsService postsService, NewsPublisher newsPublisher) {
        this.newsService = newsService;
        this.postsService = postsService;
        this.newsPublisher = newsPublisher;
    }

    GetResponse getNews() throws ClientException, ApiException {
        return newsService.getData();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> findLastNPosts(Integer count) throws ClientException, ApiException {
        GetResponse response = newsService.findLastPosts(count);
        List<Post> posts = postsService.filterPublishedRecords(response.getItems());
        return posts;
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public NewsObject create(Post post) {
        return postsService.createPost(post);
    }

    @RequestMapping(value = "posts/save", method = RequestMethod.POST)
    public void save(NewsObject newsObject) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        newsPublisher.publish(newsObject);
        postsService.postWasPublished(newsObject);
    }
}
