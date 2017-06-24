package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ToPostAttachmentConverter implements Converter<WallpostAttachment, PostAttachment> {
    @Override
    public PostAttachment convert(WallpostAttachment wallpostAttachment) {
        PostAttachment postAttachment = new PostAttachment();
        postAttachment.setLink(extractLink(wallpostAttachment));
        postAttachment.setType(extractType(wallpostAttachment));
        return postAttachment;
    }

    private PostAttachmentType extractType(WallpostAttachment wallpostAttachment) {
        return PostAttachmentType.from(wallpostAttachment.getType());
    }

    private String extractLink(WallpostAttachment wallpostAttachment) {
        if (wallpostAttachment.getLink() != null)
            return wallpostAttachment.getLink().getUrl();
        return null;
    }
}
