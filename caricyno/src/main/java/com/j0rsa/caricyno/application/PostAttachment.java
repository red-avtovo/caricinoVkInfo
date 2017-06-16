package com.j0rsa.caricyno.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostAttachment {
    private String link;
    private PostAttachmentType type;
}
