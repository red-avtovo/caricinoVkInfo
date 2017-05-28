package com.j0rsa.caricyno.db.service;

import com.j0rsa.caricyno.db.models.PostInfo;
import com.j0rsa.caricyno.db.models.PostInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.j0rsa.caricyno.db.models.PostInfoBuilder.aPostInfo;

@Service
public class PostInfoService {
    private final PostInfoDao postInfoDao;

    @Autowired
    public PostInfoService(PostInfoDao postInfoDao) {
        this.postInfoDao = postInfoDao;
    }

    public Optional<PostInfo> findPostInfo(Integer integrationId) {
        return Optional.ofNullable(postInfoDao.findByIntegrationId(integrationId));
    }

    public PostInfo saveOrUpdate(PostInfo postInfo) {
        updateIfPresent(postInfo);
        postInfoDao.save(postInfo);
        return postInfo;
    }

    private void updateIfPresent(PostInfo postInfo) {
        Optional<PostInfo> savedPostInfo = findPostInfo(postInfo.getIntegrationId());
        savedPostInfo.ifPresent(postInfo1 -> postInfo.setId(postInfo1.getId()));
    }

    public PostInfo publish(Long id) {
        PostInfo postInfo = postInfoDao.findOne(id);
        postInfo.setIsPublished(true);
        return saveOrUpdate(postInfo);
    }

    public void ignore(Long id) {
        PostInfo postInfo = postInfoDao.findOne(id);
        postInfo.setIsIgnored(true);
        saveOrUpdate(postInfo);
    }

    public PostInfo findPostOrCreateNew(Integer integrationId) {
        Optional<PostInfo> postInfoOptional = findPostInfo(integrationId);
        return postInfoOptional.orElse(aPostInfoWithIntegrationId(integrationId));
    }

    private PostInfo aPostInfoWithIntegrationId(Integer integrationId) {
        PostInfo postInfo = aPostInfo().withIntegrationId(integrationId).build();
        return saveOrUpdate(postInfo);
    }
}
