package com.j0rsa.caricyno.application.post.attachment.parser;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.function.Predicate;

public class AttachmentParser {
    static final AttachmentParser VK_VIDEO_TYPE = typeMatch(WallpostAttachmentType.VIDEO, new VkVideo());
    static final AttachmentParser LINK_TYPE = typeMatch(WallpostAttachmentType.LINK, new VkVideo());

    private static final List<AttachmentParser> typeMatches = Lists.newArrayList(
            VK_VIDEO_TYPE
    );

    private WallpostAttachmentType vkAttachmentType;
    private Attachment attachment;


    private AttachmentParser(WallpostAttachmentType vkAttachmentType, Attachment attachment) {
        this.vkAttachmentType = vkAttachmentType;
        this.attachment = attachment;
    }

    private static AttachmentParser typeMatch(WallpostAttachmentType vkAttachmentType, Attachment attachmentType) {
        return new AttachmentParser(vkAttachmentType, attachmentType);
    }

    public static PostAttachment parse(WallpostAttachment wallpostAttachment) {
        return typeMatches.stream()
                .filter(isTypeMatchedPredicate(wallpostAttachment))
                .findFirst()
                .map(attachmentParser1 -> attachmentParser1.attachment.getData(wallpostAttachment))
                .orElse(null);
    }

    private static Predicate<AttachmentParser> isTypeMatchedPredicate(final WallpostAttachment wallpostAttachment) {
        return new Predicate<AttachmentParser>() {
            @Override
            public boolean test(AttachmentParser attachmentParser) {
                return attachmentParser.isTypeMatched(wallpostAttachment.getType());
            }
        };
    }

    private boolean isTypeMatched(WallpostAttachmentType vkAttachmentType) {
        return this.vkAttachmentType.equals(vkAttachmentType);
    }
}
