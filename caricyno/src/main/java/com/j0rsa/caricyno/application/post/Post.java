package com.j0rsa.caricyno.application.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Long id;
    private String link;
    private String title;
    private String author;
    private String text;
    private Boolean isPinned;
    private Boolean isPosted;
    private Boolean isIgnored;
}
