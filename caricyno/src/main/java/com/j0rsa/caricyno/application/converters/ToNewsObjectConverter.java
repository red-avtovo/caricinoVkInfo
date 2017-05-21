package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.website.producer.NewsObject;
import com.vk.api.sdk.objects.wall.WallpostFull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ToNewsObjectConverter implements Converter<WallpostFull, NewsObject> {
    private static List<String> POST_TAGS = Arrays.asList("царицыно", "новости", "жкцарицыно", "достройтецарицыно", "дольщики");
    private static String TEMPLATE = "<p><span style=\"font-size: 12pt; font-family: 'times new roman', times;\">%s</span></p>";

    @Override
    public NewsObject convert(WallpostFull wallpostFull) {
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(wallpostFull.getText());
        newsObject.setTags(POST_TAGS);

        String text = String.format(TEMPLATE, wallpostFull.getText());
        newsObject.setHtmlText(text);

        return newsObject;
    }
}
