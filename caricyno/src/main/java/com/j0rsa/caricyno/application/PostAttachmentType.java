package com.j0rsa.caricyno.application;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

public enum PostAttachmentType {
    PHOTO("PHOTO"),
    POSTED_PHOTO("POSTED_PHOTO"),
    AUDIO("AUDIO"),
    VIDEO("VIDEO"),
    DOC("DOC"),
    LINK("LINK"),
    GRAFFITI("GRAFFITI"),
    NOTE("NOTE");

    private String name;

    PostAttachmentType(String name) {
        this.name = name;
    }

    @JsonIgnore
    public static PostAttachmentType fromString(String name) {
        return Arrays.stream(PostAttachmentType.values())
                .filter(type -> type.name.equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

}
