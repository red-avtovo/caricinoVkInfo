package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.application.services.PostService;
import com.j0rsa.caricyno.vk.NewsService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.j0rsa.caricyno.website.producer.NewsPublisher;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final PostService postService;
    private final NewsPublisher newsPublisher;

    @Autowired
    public PostController(NewsService newsService, PostService postService, NewsPublisher newsPublisher) {
        this.newsService = newsService;
        this.postService = postService;
        this.newsPublisher = newsPublisher;
    }

    GetResponse getNews() throws ClientException, ApiException {
        return newsService.getData();
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> findLastNPosts(Integer count) throws ClientException, ApiException {
        GetResponse response = newsService.findLastPosts(count);
        return postService.convertToPost(response.getItems());
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public NewsObject create(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @RequestMapping(value = "posts/save", method = RequestMethod.POST)
    public void save(NewsObject newsObject) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
        newsPublisher.publish(newsObject);
        postService.postWasPublished(newsObject);
    }

    @RequestMapping(value = "posts/ignore", method = RequestMethod.POST)
    public void save(Post post) {
        postService.ignore(post);
    }

    @RequestMapping(value = "/posts/new", method = RequestMethod.GET)
    public NewsObject createNewPost() {
        return postService.createNewPost();
    }
}
