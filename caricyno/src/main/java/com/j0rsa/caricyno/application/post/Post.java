package com.j0rsa.caricyno.application.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
