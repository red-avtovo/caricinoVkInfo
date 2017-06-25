package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.video.Video;
import com.vk.api.sdk.objects.video.VideoFiles;
import com.vk.api.sdk.objects.wall.WallpostAttachment;

public class VkVideo extends Attachment {

    @Override
    public PostAttachment getData(WallpostAttachment wallpostAttachment) {
        Video video = wallpostAttachment.getVideo();
        if (video != null) {
            return createAttachment(video);
        }

        return null;
    }

    private PostAttachment createAttachment(Video video) {
        PostAttachment postAttachment = createAttachment();
        postAttachment.setLink(getLink(video.getFiles()));
        postAttachment.setPhotoLink(video.getPhoto800());
        return postAttachment;
    }

    @Override
    public PostAttachmentType getType() {
        return PostAttachmentType.VK_VIDEO;
    }

    private String getLink(VideoFiles files) {
        if (files != null) {
            return files.getExternal();
        }
        return null;
    }
}
