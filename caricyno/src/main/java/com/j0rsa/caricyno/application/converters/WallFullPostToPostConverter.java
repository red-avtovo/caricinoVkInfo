package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.Post;
import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachments;
import com.j0rsa.caricyno.application.post.attachment.parser.AttachmentParser;
import com.j0rsa.caricyno.application.post.attachment.parser.AttachmentParserResolver;
import com.j0rsa.caricyno.vk.VkProperties;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.j0rsa.caricyno.application.Utils.isNotNull;
import static com.j0rsa.caricyno.application.Utils.wrapLinks;


@Component
public class WallFullPostToPostConverter implements Converter<WallpostFull, Post> {
    private static final String END = "<br />";
    private static final String TEMPLATE = "%s%s_%s";
    private final ConversionService conversionService;
    private final AttachmentParserResolver attachmentParserResolver;
    private final VkProperties vkProperties;

    @Autowired
    public WallFullPostToPostConverter(ConversionService conversionService, AttachmentParserResolver attachmentParserResolver, VkProperties vkProperties) {
        this.conversionService = conversionService;
        this.attachmentParserResolver = attachmentParserResolver;
        this.vkProperties = vkProperties;
    }

    @Override
    public Post convert(WallpostFull wallpostFull) {
        Post post = new Post();
        post.setLink(getLink(wallpostFull));
        post.setTitle(getTitle(wallpostFull));
        post.setText(postText(wallpostFull));
        post.setAuthor(wallpostFull.getOwnerId().toString());
        post.setIsPinned(isPinned(wallpostFull));
        addAttachments(wallpostFull, post);

        post.setText(wrapLinks(post.getText()));
        return post;
    }

    private String getLink(WallpostFull wallpostFull) {
        return String.format(TEMPLATE, vkProperties.getLink(), wallpostFull.getOwnerId(), wallpostFull.getId());
    }

    private void addAttachments(WallpostFull wallpostFull, Post post) {
        if (isNotNull(wallpostFull.getAttachments())) {
            for (WallpostAttachment attachment : wallpostFull.getAttachments()) {
                parseAttachment(post, attachment);
            }
        }
    }

    private void parseAttachment(Post post, WallpostAttachment attachment) {
        Optional<AttachmentParser> attachmentParserOptional = attachmentParserResolver.resolve(attachment);
        if (attachmentParserOptional.isPresent()) {
            PostAttachments postAttachments = attachmentParserOptional.get().parse(attachment);
            if (isNotNull(postAttachments)) {
                post.setText(attachAttachmentInfo(post, postAttachments));
            }
        }
    }

    private String attachAttachmentInfo(Post post, PostAttachments postAttachments) {
        String postAttachmentText = getTextWithEndOrEmpty(post.getText());
        for (PostAttachment postAttachment : postAttachments.getPostAttachments()) {
            postAttachmentText = postAttachmentText.concat(getTextWithEndOrEmpty(conversionService.convert(postAttachment, String.class)));
        }
        return postAttachmentText;
    }

    private String getTextWithEndOrEmpty(String text) {
        if (isNotNull(text)) {
            return text.concat(END);
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
