package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.j0rsa.caricyno.application.post.attachment.PostAttachments;
import com.vk.api.sdk.objects.video.Video;
import com.vk.api.sdk.objects.video.VideoFiles;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.stereotype.Service;

import static com.j0rsa.caricyno.application.Utils.isNotNull;
import static com.j0rsa.caricyno.application.post.attachment.PostAttachments.postAttachments;

@Service
public class VkVideo extends AttachmentParser {

    @Override
    public PostAttachments parse(WallpostAttachment wallpostAttachment) {
        Video video = wallpostAttachment.getVideo();
        if (isNotNull(video)) {
            return postAttachments(attachment(video));
        }

        return null;
    }

    private PostAttachment attachment(Video video) {
        PostAttachment postAttachment = createAttachment();
        postAttachment.setLink(getLink(video.getFiles()));
        postAttachment.setPhotoLink(extractPhoto(video));
        return postAttachment;
    }

    private String extractPhoto(Video video) {
        if (isNotNull(video.getPhoto800())) {
            return video.getPhoto800();
        } else if (isNotNull(video.getPhoto320())) {
            return video.getPhoto320();
        }
        return video.getPhoto130();
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
