package com.j0rsa.caricyno.application.converters;

import com.j0rsa.caricyno.application.Post;
import com.j0rsa.caricyno.website.producer.NewsObject;
import com.j0rsa.caricyno.website.producer.enums.ArticleRights;
import com.j0rsa.caricyno.website.producer.enums.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ToNewsObjectConverter implements Converter<Post, NewsObject> {
    private static List<String> POST_TAGS = Arrays.asList("царицыно", "новости", "жкцарицыно", "достройтецарицыно", "дольщики");
    private static String TEMPLATE = "<p><span style=\"font-size: 12pt; font-family: 'times new roman', times;\">%s</span></p>";
    private static Category DEFAULT_CATEGORY = Category.OTHER;
    private static ArticleRights DEFAULT_ARTICLES_RIGHTS = ArticleRights.OWNER;
    private static ArticleRights DEFAULT_COMMENT_RIGHTS = ArticleRights.EVERYONE;

    @Override
    public NewsObject convert(Post post) {
        NewsObject newsObject = new NewsObject();
        newsObject.setTitle(post.getTitle());
        newsObject.setHtmlText(formattedText(post));

        newsObject.setTags(POST_TAGS);
        newsObject.setCategory(DEFAULT_CATEGORY);
        newsObject.setVisibility(DEFAULT_ARTICLES_RIGHTS);
        newsObject.setCommentsRights(DEFAULT_COMMENT_RIGHTS);
        newsObject.setVisibleInSearchEngines(true);

        return newsObject;
    }

    private String formattedText(Post post) {
        return String.format(TEMPLATE, post.getText());
    }
}
