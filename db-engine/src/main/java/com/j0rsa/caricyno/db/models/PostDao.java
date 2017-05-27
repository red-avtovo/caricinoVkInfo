package com.j0rsa.caricyno.db.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface PostDao extends CrudRepository<Post, Long> {
    public Post findByIntegrationId(Integer integrationId);
}
