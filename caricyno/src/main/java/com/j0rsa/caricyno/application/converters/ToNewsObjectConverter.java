package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.post.Post;
import com.j0rsa.caricyno.website.producer.NewsObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.j0rsa.caricyno.website.producer.NewsObjectBuilder.aNewsObject;

@Component
public class ToNewsObjectConverter implements Converter<Post, NewsObject> {

    @Override
    public NewsObject convert(Post post) {
        return aNewsObject()
                .withId(post.getId())
                .withDefaultTags()
                .withDefaultCategory()
                .withDefaultVisibility()
                .withDefaultCommentsRights()
                .withDefaultVisibleInSearchEngines()
                .withTitle(post.getTitle())
                .withHtmlText(post.getText())
                .buildNewsObject();
    }

}
