package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AttachmentToStringConverter implements Converter<PostAttachment, String> {
    private static String TEMPLATE = "%s %s %s";

    @Override
    public String convert(PostAttachment postAttachment) {
        return String.format(
                TEMPLATE,
                getLink(postAttachment.getLink()),
                getLink(postAttachment.getPhotoLink()),
                postAttachment.getType().name()
        );
    }

    private String getLink(String link) {
        if (link == null) {
            link = "---link---";
        }
        return link;
    }
}
