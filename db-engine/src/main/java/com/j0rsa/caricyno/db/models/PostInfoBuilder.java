package com.j0rsa.caricyno.db.models;

public class PostInfoBuilder {
    private static Integer DEFAULT_INTEGRATION_ID = Integer.MIN_VALUE;
    private Long id;
    private Integer integrationId;
    private Boolean isPublished = false;
    private Boolean isIgnored = false;

    private PostInfoBuilder() {

    }

    public static PostInfoBuilder aPostInfo() {
        return new PostInfoBuilder();
    }

    public PostInfoBuilder withIntegrationId(Integer integrationId) {
        this.integrationId = integrationId;
        return this;
    }

    public PostInfoBuilder withDefaultIntegrationId() {
        this.integrationId = DEFAULT_INTEGRATION_ID;
        return this;
    }

    public PostInfo build() {
        PostInfo postInfo = new PostInfo();
        postInfo.setId(id);
        postInfo.setIntegrationId(integrationId);
        postInfo.setIsPublished(isPublished);
        postInfo.setIsIgnored(isIgnored);
        return postInfo;
    }
}
