package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.base.Link;
import com.vk.api.sdk.objects.wall.WallpostAttachment;

public class LinkVideo extends Attachment {
    @Override
    public PostAttachment getData(WallpostAttachment wallpostAttachment) {
        Link link = wallpostAttachment.getLink();
        if (link != null) {
            return createAttachment(link);
        }

        return null;
    }

    private PostAttachment createAttachment(Link link) {
        PostAttachment postAttachment = createAttachment();
        postAttachment.setLink(link.getUrl());
        return postAttachment;
    }

    @Override
    public PostAttachmentType getType() {
        return PostAttachmentType.LINK;
    }
}
