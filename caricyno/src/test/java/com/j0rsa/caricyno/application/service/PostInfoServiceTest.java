package com.j0rsa.caricyno.application.service;

import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.service.PostInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        PostInfo savedPostInfo = postInfoService.saveOrUpdate(postInfo);

        // then
        assertThat(savedPostInfo.getId()).isNotNull();
    }

    @Test
    public void whenPostIsNotSavedThenPostInfoNotPresent() {
        // given
        Integer notSavedPostIntegrationId = 1;

        // when
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(notSavedPostIntegrationId);

        // then
        assertThat(postInfo).isNotPresent();
    }

    @Test
    public void whenPostIsSavedButNotPublishedThenNotPosted() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(integrationId);

        // then
        assertThat(postInfo).isPresent();
        assertThat(postInfo.get().isPosted()).isFalse();
    }

    @Test
    public void whenPostIsPublishedThenPosted() {
        // given
        Integer integrationId = 1;
        saveAndPublish(integrationId);

        // when
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(integrationId);

        // then
        assertThat(postInfo).isPresent();
        assertThat(postInfo.get().isPosted()).isTrue();
    }

    @Test
    public void whenPostIsSavedThenNotIgnored() {
        // given
        Integer integrationId = 1;
        savePost(integrationId);

        // when
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(integrationId);

        // then
        assertThat(postInfo).isPresent();
        assertThat(postInfo.get().isIgnored()).isFalse();
    }

    @Test
    public void whenPostIsSavedAndPublishedThenNotIgnored() {
        // given
        Integer integrationId = 1;
        saveAndPublish(integrationId);

        // when
        Optional<PostInfo> postInfo = postInfoService.findPostInfo(integrationId);

        // then
        assertThat(postInfo).isPresent();
        assertThat(postInfo.get().isIgnored()).isFalse();
    }

    @Test
    public void whenPostIsSavedAndPublishedAndIgnoredThenIgnored() {
        // given
        Integer integrationId = 1;
        PostInfo postInfo = saveAndPublish(integrationId);
        postInfoService.ignore(postInfo.getId());

        // when
        Optional<PostInfo> foundPostInfo = postInfoService.findPostInfo(integrationId);

        // then
        assertThat(foundPostInfo).isPresent();
        assertThat(foundPostInfo.get().isIgnored()).isTrue();
    }

    private PostInfo saveAndPublish(Integer integrationId) {
        PostInfo savedPostInfo = savePost(integrationId);
        postInfoService.publish(savedPostInfo.getId());
        return savedPostInfo;
    }

    private PostInfo savePost(Integer integrationId) {
        PostInfo postInfo = new PostInfo();
        postInfo.setIntegrationId(integrationId);
        return postInfoService.saveOrUpdate(postInfo);
    }
}