package com.j0rsa.caricyno.application;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

@Data
public class Post {
    private Long id;
    private String title;
    private String author;
    private String text;
    private Boolean isPinned;
    private Boolean isPosted;
    private Boolean isIgnored;
    private List<PostAttachment> postAttachments = Lists.newArrayList();

    public void add(PostAttachment postAttachment) {
        postAttachments.add(postAttachment);
    }
}
