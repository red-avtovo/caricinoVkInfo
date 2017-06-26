package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.Post;
import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
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
    private static final String END = "<br />";

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
        post.setText(postText(wallpostFull));
        post.setAuthor(wallpostFull.getOwnerId().toString());
        post.setIsPinned(isPinned(wallpostFull));
        addAttachments(wallpostFull, post);

        post.setText(wrapLinks(post.getText()));
        return post;
    }

    private void addAttachments(WallpostFull wallpostFull, Post post) {
        if (wallpostFull.getAttachments() != null) {
            for (WallpostAttachment attachment : wallpostFull.getAttachments()) {
                PostAttachment postAttachment = parse(attachment);
                if (postAttachment != null) {
                    post.add(postAttachment);
                    post.setText(allTextWithAttachmentTExtAtTheEnd(post, postAttachment));
                }
            }
        }
    }

    private String allTextWithAttachmentTExtAtTheEnd(Post post, PostAttachment postAttachment) {
        return post.getText().concat(END).concat(conversionService.convert(postAttachment, String.class));
    }

    private String postText(WallpostFull wallpostFull) {
        return wallpostFull.getText().replaceAll("\r\n", END).replaceAll("\n", END);
    }

    private boolean isPinned(WallpostFull wallpostFull) {
        return wallpostFull.getIsPinned() != null && wallpostFull.getIsPinned() == 1;
    }

    private String getTitle(WallpostFull wallpostFull) {
        return wallpostFull.getText().split("\n")[0];
    }
}
