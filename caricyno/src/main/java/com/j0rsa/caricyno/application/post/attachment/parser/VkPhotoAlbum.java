package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.j0rsa.caricyno.application.post.attachment.PostAttachments;
import com.j0rsa.caricyno.vk.PhotoAlbumService;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoAlbum;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.j0rsa.caricyno.application.Utils.isNotNull;
import static com.j0rsa.caricyno.application.post.attachment.PostAttachments.postAttachments;

@Service
public class VkPhotoAlbum extends AttachmentParser {
    private final PhotoAlbumService photoAlbumService;
    private final VkPhoto vkPhoto;

    @Autowired
    public VkPhotoAlbum(PhotoAlbumService photoAlbumService, VkPhoto vkPhoto) {
        this.photoAlbumService = photoAlbumService;
        this.vkPhoto = vkPhoto;
    }

    @Override
    public PostAttachments parse(WallpostAttachment wallpostAttachment) {
        PostAttachments postAttachments = postAttachments();
        if (isNotNull(wallpostAttachment.getAlbum())) {
            List<Photo> photos = findAlbumPhotos(wallpostAttachment.getAlbum());
            if (isNotNull(photos)) {
                createAttachmentsFromPhotos(postAttachments, photos);
            }
        }
        return postAttachments;
    }

    private void createAttachmentsFromPhotos(PostAttachments postAttachments, List<Photo> photos) {
        for (Photo photo : photos) {
            PostAttachment postAttachment = vkPhoto.postAttachment(photo);
            postAttachments.add(postAttachment);
        }
    }

    private List<Photo> findAlbumPhotos(PhotoAlbum album) {
        try {
            GetResponse albumResponse = photoAlbumService.findPhotos(album.getOwnerId(), album.getId());
            return albumResponse.getItems();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    PostAttachmentType getType() {
        return PostAttachmentType.VK_PHOTO_ALBUM;
    }
}
