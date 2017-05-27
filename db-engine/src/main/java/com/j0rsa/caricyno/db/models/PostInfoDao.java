package com.j0rsa.caricyno.db.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PostInfoDao extends CrudRepository<PostInfo, Long> {
    public PostInfo findByIntegrationId(Integer integrationId);
}
