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
        return post != null && post.getIsPublished();
    }

    public Post save(Post post) {
        postDao.save(post);
        return post;
    }

    public Post publish(Long id) {
        Post post = postDao.findOne(id);
        post.setIsPublished(true);
        return save(post);
    }
}
