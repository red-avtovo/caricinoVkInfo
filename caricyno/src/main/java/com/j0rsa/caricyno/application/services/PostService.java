package com.j0rsa.caricyno.application.services;

import com.j0rsa.caricyno.application.Post;
import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.service.PostInfoService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.j0rsa.caricyno.website.producer.NewsPublisher;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostInfoService postInfoService;
    private final ConversionService conversionService;

    @Autowired
    public PostService(PostInfoService postInfoService, ConversionService conversionService, NewsPublisher newsPublisher) {
        this.postInfoService = postInfoService;
        this.conversionService = conversionService;
    }

    public List<Post> filterPublishedRecords(List<WallpostFull> wallposts) {
        List<Post> posts = Lists.newArrayList();
        for (WallpostFull wallpost : wallposts) {
            if (isNotPosted(wallpost)) {
                posts.add(conversionService.convert(wallpost, Post.class));
            }
        }
        return posts;
    }

    private boolean isNotPosted(WallpostFull wallpost) {
        return !postInfoService.isPosted(wallpost.getId());
    }

    public NewsObject createPost(Post post) {
        NewsObject newsObject = conversionService.convert(post, NewsObject.class);
        updateNewsObjectWithPostId(post, newsObject);
        return newsObject;
    }

    public void postWasPublished(NewsObject newsObject) {
        postInfoService.publish(newsObject.getId());
    }

    private void updateNewsObjectWithPostId(Post post, NewsObject newsObject) {
        Long postId = savePostInfo(post);
        newsObject.setId(postId);
    }

    private Long savePostInfo(Post post) {
        PostInfo postInfoInfo = new PostInfo();
        postInfoInfo.setIntegrationId(post.getId());
        PostInfo savedInfo = postInfoService.save(postInfoInfo);
        return savedInfo.getId();
    }
}
