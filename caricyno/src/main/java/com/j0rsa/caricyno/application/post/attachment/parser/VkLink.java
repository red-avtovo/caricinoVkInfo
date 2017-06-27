package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.j0rsa.caricyno.application.post.attachment.PostAttachments;
import com.vk.api.sdk.objects.base.Link;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.stereotype.Service;

import static com.j0rsa.caricyno.application.post.attachment.PostAttachments.postAttachments;

@Service
public class VkLink extends AttachmentParser {
    @Override
    public PostAttachments parse(WallpostAttachment wallpostAttachment) {
        Link link = wallpostAttachment.getLink();
        if (link != null) {
            return createAttachment(link);
        }

        return null;
    }

    private PostAttachments createAttachment(com.vk.api.sdk.objects.base.Link link) {
        PostAttachment postAttachment = createAttachment();
        postAttachment.setLink(link.getUrl());
        return postAttachments(postAttachment);
    }

    @Override
    public PostAttachmentType getType() {
        return PostAttachmentType.LINK;
    }
}
