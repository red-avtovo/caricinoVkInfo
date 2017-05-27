package com.j0rsa.caricyno.application.service;

import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.service.PostInfoService;
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
public class PostInfoServiceTest {
    @Autowired
    private PostInfoService postInfoService;

    @Test
    public void testSavePost() {
        // given
        PostInfo postInfo = new PostInfo();
        postInfo.setIntegrationId(1);

        // when
        PostInfo savedPostInfo = postInfoService.save(postInfo);

        // then
        assertThat(savedPostInfo.getId()).isNotNull();
    }

    @Test
    public void whenPostIsNotSavedThenNotPosted() {
        // given
        Integer notSavedPostIntegrationId = 1;

        // when
        boolean posted = postInfoService.isPosted(notSavedPostIntegrationId);

        // then
        assertThat(posted).isFalse();
    }

    @Test
    public void whenPostIsSavedThenPosted() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        boolean posted = postInfoService.isPosted(integrationId);

        // then
        assertThat(posted).isTrue();
    }

    @Test
    public void whenPostIsPublishedThenPosted() {
        // given
        Integer integrationId = 1;
        PostInfo savedPostInfo = savePost(integrationId);
        postInfoService.publish(savedPostInfo.getId());

        // when
        boolean posted = postInfoService.isPosted(integrationId);

        // then
        assertThat(posted).isTrue();
    }

    @Test
    public void whenPostIsSavedButNotPublishedThenNotPosted() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        boolean posted = postInfoService.isPosted(integrationId);

        // then
        assertThat(posted).isFalse();
    }

    private PostInfo savePost(Integer integrationId) {
        PostInfo postInfo = new PostInfo();
        postInfo.setIntegrationId(integrationId);
        return postInfoService.save(postInfo);
    }
}