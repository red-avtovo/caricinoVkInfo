package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.Post;
import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.parser.AttachmentParser;
import com.j0rsa.caricyno.application.post.attachment.parser.AttachmentParserResolver;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class WallFullPostToPostConverter implements Converter<WallpostFull, Post> {
    private static final String END = "<br />";
    private final ConversionService conversionService;
    private final AttachmentParserResolver attachmentParserResolver;

    @Autowired
    public WallFullPostToPostConverter(ConversionService conversionService, AttachmentParserResolver attachmentParserResolver) {
        this.conversionService = conversionService;
        this.attachmentParserResolver = attachmentParserResolver;
    }

    public static String wrapLinks(String text) {
        return text
                .replaceAll("([http|ftp|https]+://[^<> ]+)", "<a href='$1'>$1</a>");
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
                Optional<AttachmentParser> attachmentParserOptional = attachmentParserResolver.resolve(attachment);
                if (attachmentParserOptional.isPresent()) {
                    PostAttachment postAttachment = attachmentParserOptional.get().parse(attachment);
                    if (postAttachment != null) {
                        post.add(postAttachment);
                        post.setText(allTextWithAttachmentTExtAtTheEnd(post, postAttachment));
                    }
                }
            }
        }
    }

    private String allTextWithAttachmentTExtAtTheEnd(Post post, PostAttachment postAttachment) {
        return getTextWithEndOrEmpty(post).concat(conversionService.convert(postAttachment, String.class));
    }

    private String getTextWithEndOrEmpty(Post post) {
        if (post.getText() != null) {
            return post.getText().concat(END);
        }
        return "";
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
