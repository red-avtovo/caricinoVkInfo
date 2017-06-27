package com.j0rsa.caricyno.application.post.attachment;

import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

@Data
public class PostAttachments {
    private List<PostAttachment> postAttachments = Lists.newArrayList();

    private PostAttachments(List<PostAttachment> postAttachments) {
        this.postAttachments = postAttachments;
    }

    private PostAttachments() {
    }

    public static PostAttachments postAttachments(List<PostAttachment> postAttachments) {
        return new PostAttachments(postAttachments);
    }

    public static PostAttachments postAttachments(PostAttachment postAttachment) {
        PostAttachments postAttachments = new PostAttachments();
        postAttachments.add(postAttachment);
        return postAttachments;
    }

    public void add(PostAttachment postAttachment) {
        this.postAttachments.add(postAttachment);
    }

    public void addAll(List<PostAttachment> postAttachments) {
        this.postAttachments.addAll(postAttachments);
    }
}
