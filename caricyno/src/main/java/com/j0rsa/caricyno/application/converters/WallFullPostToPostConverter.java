package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.Post;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WallFullPostToPostConverter implements Converter<WallpostFull, Post> {

    public static String wrapLinks(String text) {
        return text.replaceAll("([http|ftp]+://[^<> ]+)", "<a href='$1'>$1</a>");
    }

    @Override
    public Post convert(WallpostFull wallpostFull) {
        Post post = new Post();
        post.setTitle(getTitle(wallpostFull));
        post.setText(wrapLinks(postText(wallpostFull)));
        post.setAuthor(wallpostFull.getOwnerId().toString());
        post.setIsPinned(isPinned(wallpostFull));
        return post;
    }

    private String postText(WallpostFull wallpostFull) {
        return wallpostFull.getText().replaceAll("\r\n", "<br />").replaceAll("\n", "<br />");
    }

    private boolean isPinned(WallpostFull wallpostFull) {
        return wallpostFull.getIsPinned() != null && wallpostFull.getIsPinned() == 1;
    }

    private String getTitle(WallpostFull wallpostFull) {
        return wallpostFull.getText().split("\n")[0];
    }
}
