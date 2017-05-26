package com.j0rsa.caricyno.application;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private String title;
    private String author;
    private String text;
    private Boolean isPinned;
}
