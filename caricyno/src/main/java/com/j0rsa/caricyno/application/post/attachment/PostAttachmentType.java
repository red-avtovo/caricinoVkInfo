package com.j0rsa.caricyno.application.post.attachment;

public enum PostAttachmentType {
    VK_PHOTO("VK_PHOTO"),
    POSTED_PHOTO("POSTED_PHOTO"),
    AUDIO("AUDIO"),
    VK_VIDEO("VK_VIDEO"),
    YOUTUBE_VIDEO("YOUTUBE_VIDEO"),
    DOC("DOC"),
    LINK("LINK"),
    GRAFFITI("GRAFFITI"),
    NOTE("NOTE");

    private String name;

    PostAttachmentType(String name) {
        this.name = name;
    }
}
