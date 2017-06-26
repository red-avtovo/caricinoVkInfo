package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.attachment.PostAttachment;
import com.vk.api.sdk.objects.base.Link;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static com.j0rsa.caricyno.application.post.attachment.PostAttachmentType.LINK;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ToPostAttachmentParserConverterTest {
    public static final String TEST_LINK = "testLink";

    @Autowired
    private ConversionService conversionService;

    @Test
    public void whenConvertLinkWallPostAttachmentThenLinkPostAttachment() throws Exception {
        // given
        WallpostAttachment linkWallPostAttachment = createLinkWallPostAttachment();

        // when
        PostAttachment resultPostAttachment = conversionService.convert(linkWallPostAttachment, PostAttachment.class);

        // then
        assertThatResultIsLinkPostAttachment(resultPostAttachment);
    }

    private void assertThatResultIsLinkPostAttachment(PostAttachment resultPostAttachment) {
        assertThat(resultPostAttachment.getLink()).isEqualTo(TEST_LINK);
        assertThat(resultPostAttachment.getType()).isEqualTo(LINK);
    }

    private WallpostAttachment createLinkWallPostAttachment() throws NoSuchFieldException, IllegalAccessException {
        WallpostAttachment wallpostAttachment = new WallpostAttachment();
        setField(wallpostAttachment, "link", testLink());
        setField(wallpostAttachment, "type", WallpostAttachmentType.LINK);
        return wallpostAttachment;
    }

    private Link testLink() throws NoSuchFieldException, IllegalAccessException {
        Link link = new Link();
        setField(link, Link.class, "url", TEST_LINK);
        return link;
    }

    private void setField(Object object, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        setField(object, WallpostAttachment.class, fieldName, fieldValue);
    }

    private void setField(Object object, Class clazz, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
    }

}