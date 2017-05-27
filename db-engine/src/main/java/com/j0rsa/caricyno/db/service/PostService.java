package com.j0rsa.caricyno.db.service;

import com.j0rsa.caricyno.db.models.Post;
import com.j0rsa.caricyno.db.models.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostDao postDao;

    @Autowired
    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public boolean isPosted(Integer integrationId) {
        Post post = postDao.findByIntegrationId(integrationId);
        return post != null;
    }

    public Post save(Post post) {
        postDao.save(post);
        return post;
    }
}
