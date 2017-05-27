package com.j0rsa.caricyno.application.services;

import com.j0rsa.caricyno.application.Post;
import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.models.PostInfoBuilder;
import com.j0rsa.caricyno.db.service.PostInfoService;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            Post post = conversionService.convert(wallpost, Post.class);
            enrichWithPostInfo(post);
            posts.add(post);
        }
        return posts;
    }

    private void enrichWithPostInfo(Post post) {
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(post.getId());
        post.setIsPosted(isPosted(postInfo));
        post.setIsIgnored(isIgnored(postInfo));
    }

    public NewsObject createPost(Post post) {
        NewsObject newsObject = conversionService.convert(post, NewsObject.class);
        enrichIntegratedNewsObject(post.getId(), newsObject);
        return newsObject;
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
        updateNewsObjectWithPostId(aPostInfo().withDefaultIntegrationId(), newsObject);
    }

    private void enrichIntegratedNewsObject(Integer integrationId, NewsObject newsObject) {
        updateNewsObjectWithPostId(aPostInfo().withIntegrationId(integrationId), newsObject);
    }

    private void updateNewsObjectWithPostId(PostInfoBuilder aPostInfo, NewsObject newsObject) {
        Long postId = savePostInfo(aPostInfo);
        newsObject.setId(postId);
    }

    private Long savePostInfo(PostInfoBuilder aPostInfo) {
        PostInfo postInfo = aPostInfo.build();
        PostInfo savedInfo = postInfoService.save(postInfo);
        return savedInfo.getId();
    }

    private Boolean isPosted(Optional<PostInfo> postInfo) {
        return postInfo.map(PostInfo::isPosted).orElse(false);
    }

    private Boolean isIgnored(Optional<PostInfo> postInfo) {
        return postInfo.map(PostInfo::isPosted).orElse(false);
    }

    public void ignore(Post post) {
        postInfoService.ignore(post.getId());
    }
}
