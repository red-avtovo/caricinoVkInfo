package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.Post;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.j0rsa.caricyno.application.post.attachment.parser.AttachmentParser.parse;

@Component
public class WallFullPostToPostConverter implements Converter<WallpostFull, Post> {
    private final ConversionService conversionService;

    @Autowired
    public WallFullPostToPostConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public static String wrapLinks(String text) {
        return text.replaceAll("([http|ftp|https]+://[^<> ]+)", "<a href='$1'>$1</a>");
    }

    @Override
    public Post convert(WallpostFull wallpostFull) {
        Post post = new Post();
        post.setTitle(getTitle(wallpostFull));
        post.setText(wrapLinks(postText(wallpostFull)));
        post.setAuthor(wallpostFull.getOwnerId().toString());
        post.setIsPinned(isPinned(wallpostFull));
        addAttachments(wallpostFull, post);

        return post;
    }

    private void addAttachments(WallpostFull wallpostFull, Post post) {
        if (wallpostFull.getAttachments() != null) {
            for (WallpostAttachment attachment : wallpostFull.getAttachments()) {
                post.add(parse(attachment));
            }
        }
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
