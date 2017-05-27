package com.j0rsa.caricyno.application.service;

import com.j0rsa.caricyno.db.models.Post;
import com.j0rsa.caricyno.db.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Test
    public void testSavePost() {
        // given
        Post post = new Post();
        post.setIntegrationId(1);

        // when
        Post savedPost = postService.save(post);

        // then
        assertThat(savedPost.getId()).isNotNull();
    }

    @Test
    public void whenPostIsNotSavedThenNotPosted() {
        // given
        Integer notSavedPostIntegrationId = 1;

        // when
        boolean posted = postService.isPosted(notSavedPostIntegrationId);

        // then
        assertThat(posted).isFalse();
    }

    @Test
    public void whenPostIsSavedThenPosted() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        boolean posted = postService.isPosted(integrationId);

        // then
        assertThat(posted).isTrue();
    }

    @Test
    public void whenPostIsPublishedThenPosted() {
        // given
        Integer integrationId = 1;
        Post savedPost = savePost(integrationId);
        postService.publish(savedPost.getId());

        // when
        boolean posted = postService.isPosted(integrationId);

        // then
        assertThat(posted).isTrue();
    }

    @Test
    public void whenPostIsSavedButNotPublishedThenNotPosted() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        boolean posted = postService.isPosted(integrationId);

        // then
        assertThat(posted).isFalse();
    }

    private Post savePost(Integer integrationId) {
        Post post = new Post();
        post.setIntegrationId(integrationId);
        return postService.save(post);
    }
}