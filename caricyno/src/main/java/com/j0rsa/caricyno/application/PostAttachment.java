package com.j0rsa.caricyno.application;

import lombok.Data;

@Data
public class PostAttachment {
    private String link;
    private PostAttachmentType type;
}
