package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.Post;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WallFullPostToPostConverter implements Converter<WallpostFull, Post> {

    @Override
    public Post convert(WallpostFull wallpostFull) {
        Post post = new Post();
        post.setId(wallpostFull.getId());
        post.setTitle(getTitle(wallpostFull));
        post.setText(wallpostFull.getText());
        post.setAuthor(wallpostFull.getOwnerId().toString());
        post.setIsPinned(isPinned(wallpostFull));
        return post;
    }

    private boolean isPinned(WallpostFull wallpostFull) {
        return wallpostFull.getIsPinned() != null && wallpostFull.getIsPinned() == 1;
    }

    private String getTitle(WallpostFull wallpostFull) {
        return wallpostFull.getText().split("\n")[0];
    }
}
