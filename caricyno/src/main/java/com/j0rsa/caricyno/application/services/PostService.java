package com.j0rsa.caricyno.application.services;

import com.j0rsa.caricyno.application.post.Post;
import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.service.PostInfoService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.j0rsa.caricyno.db.models.PostInfoBuilder.aPostInfo;
import static com.j0rsa.caricyno.website.producer.NewsObjectBuilder.aNewsObject;

@Service
public class PostService {
    private final PostInfoService postInfoService;
    private final ConversionService conversionService;

    @Autowired
    public PostService(PostInfoService postInfoService, ConversionService conversionService) {
        this.postInfoService = postInfoService;
        this.conversionService = conversionService;
    }

    public List<Post> convertToPost(List<WallpostFull> wallposts) {
        List<Post> posts = Lists.newArrayList();
        for (WallpostFull wallpost : wallposts) {
            PostInfo postInfo = postInfoService.findPostOrCreateNew(wallpost.getId());
            Post post = conversionService.convert(wallpost, Post.class);
            enrichWithPostInfo(post, postInfo);
            posts.add(post);
        }
        return posts;
    }

    private void enrichWithPostInfo(Post post, PostInfo postInfo) {
        post.setId(postInfo.getId());
        post.setIsPosted(postInfo.isPosted());
        post.setIsIgnored(postInfo.isIgnored());
    }

    public NewsObject createPost(Post post) {
        return conversionService.convert(post, NewsObject.class);
    }

    public void postWasPublished(NewsObject newsObject) {
        postInfoService.publish(newsObject.getId());
    }

    public NewsObject createNewPost() {
        NewsObject newsObject = aNewsObject()
                .withDefaultTags()
                .withDefaultText()
                .withDefaultCategory()
                .withDefaultVisibility()
                .withDefaultCommentsRights()
                .withDefaultVisibleInSearchEngines()
                .buildNewsObject();
        enrichNewNewsObject(newsObject);
        return newsObject;
    }

    private void enrichNewNewsObject(NewsObject newsObject) {
        Long postId = savePostInfo();
        newsObject.setId(postId);
    }

    private Long savePostInfo() {
        PostInfo postInfo = aPostInfo()
                .withDefaultIntegrationId()
                .build();
        PostInfo savedInfo = postInfoService.save(postInfo);
        return savedInfo.getId();
    }

    public void ignore(Post post) {
        postInfoService.ignore(post.getId());
    }
}
