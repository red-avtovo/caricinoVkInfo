package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostAttachment;

public abstract class Attachment {
    abstract PostAttachment getData(WallpostAttachment wallpostAttachment);

    abstract PostAttachmentType getType();

    PostAttachment createAttachment() {
        PostAttachment postAttachment = new PostAttachment();
        postAttachment.setType(getType());
        return postAttachment;
    }
}
