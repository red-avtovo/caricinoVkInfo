package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachmentType;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AttachmentParserResolver {
    private static final Map<WallpostAttachmentType, PostAttachmentType> typeMatches;

    static {
        typeMatches = new HashMap<>();
        typeMatches.put(WallpostAttachmentType.VIDEO, PostAttachmentType.VK_VIDEO);
        typeMatches.put(WallpostAttachmentType.LINK, PostAttachmentType.LINK);
        typeMatches.put(WallpostAttachmentType.PHOTO, PostAttachmentType.VK_PHOTO);
        typeMatches.put(WallpostAttachmentType.ALBUM, PostAttachmentType.VK_PHOTO_ALBUM);
    }

    private final List<AttachmentParser> attachmentParsers;


    @Autowired
    private AttachmentParserResolver(List<AttachmentParser> attachmentParsers) {
        this.attachmentParsers = attachmentParsers;
    }

    public Optional<AttachmentParser> resolve(WallpostAttachment wallpostAttachment) {
        PostAttachmentType postAttachmentType = typeMatches.get(wallpostAttachment.getType());
        for (AttachmentParser attachmentParser : attachmentParsers) {
            if (attachmentParser.getType().equals(postAttachmentType)) {
                return Optional.of(attachmentParser);
            }
        }
        return Optional.empty();
    }
}
