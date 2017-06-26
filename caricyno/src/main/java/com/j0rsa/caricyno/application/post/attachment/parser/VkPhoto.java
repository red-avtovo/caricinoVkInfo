package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.stereotype.Service;

import static com.j0rsa.caricyno.application.Utils.isNotNull;

@Service
public class VkPhoto extends AttachmentParser {
    @Override
    public PostAttachment parse(WallpostAttachment wallpostAttachment) {
        PostAttachment postAttachment = createAttachment();
        Photo photo = wallpostAttachment.getPhoto();
        if (isNotNull(photo)) {
            postAttachment.setLink(getPhoto(photo));
        }
        return postAttachment;
    }

    private String getPhoto(Photo photo) {
        if (isNotNull(photo.getPhoto604())) {
            return photo.getPhoto604();
        } else if (isNotNull(photo.getPhoto130())) {
            return photo.getPhoto130();
        }
        return photo.getPhoto75();
    }

    @Override
    PostAttachmentType getType() {
        return PostAttachmentType.VK_PHOTO;
    }
}
