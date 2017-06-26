package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.stereotype.Service;

@Service
public class VkLink extends AttachmentParser {
    @Override
    public PostAttachment parse(WallpostAttachment wallpostAttachment) {
        com.vk.api.sdk.objects.base.Link link = wallpostAttachment.getLink();
        if (link != null) {
            return createAttachment(link);
        }

        return null;
    }

    private PostAttachment createAttachment(com.vk.api.sdk.objects.base.Link link) {
        PostAttachment postAttachment = createAttachment();
        postAttachment.setLink(link.getUrl());
        return postAttachment;
    }

    @Override
    public PostAttachmentType getType() {
        return PostAttachmentType.LINK;
    }
}
