package com.j0rsa.caricyno.website.producer;

import com.j0rsa.caricyno.website.producer.enums.ArticleRights;
import com.j0rsa.caricyno.website.producer.enums.Category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsObjectBuilder {
    private static List<String> POST_TAGS = Arrays.asList("царицыно", "новости", "жкцарицыно", "достройтецарицыно", "дольщики");
    private static String TEMPLATE = "<p><span style=\"font-size: 12pt; font-family: 'times new roman', times;\">%s</span></p>";
    private static Category DEFAULT_CATEGORY = Category.OTHER;
    private static ArticleRights DEFAULT_ARTICLES_RIGHTS = ArticleRights.OWNER;
    private static ArticleRights DEFAULT_COMMENT_RIGHTS = ArticleRights.EVERYONE;
    private Long id;
    private String title;
    private Category category;
    private URL mainPhoto;
    private String htmlText;
    private boolean visibleInSearchEngines;
    private List<String> tags = new ArrayList<>();
    private ArticleRights visibility;
    private ArticleRights commentsRights;

    private NewsObjectBuilder() {

    }

    public static NewsObjectBuilder aNewsObject() {
        return new NewsObjectBuilder();
    }

    public NewsObjectBuilder withDefaultTags() {
        this.tags = POST_TAGS;
        return this;
    }

    public NewsObjectBuilder withDefaultCategory() {
        this.category = DEFAULT_CATEGORY;
        return this;
    }

    public NewsObjectBuilder withDefaultVisibility() {
        this.visibility = DEFAULT_ARTICLES_RIGHTS;
        return this;
    }

    public NewsObjectBuilder withDefaultCommentsRights() {
        this.commentsRights = DEFAULT_COMMENT_RIGHTS;
        return this;
    }

    public NewsObjectBuilder withDefaultVisibleInSearchEngines() {
        this.visibleInSearchEngines = false;
        return this;
    }

    public NewsObjectBuilder withDefaultText() {
        this.htmlText = String.format(TEMPLATE, "");
        return this;
    }

    public NewsObject buildNewsObject() {
        NewsObject newsObject = new NewsObject();
        newsObject.setId(id);
        newsObject.setTags(tags);
        newsObject.setTitle(title);
        newsObject.setHtmlText(htmlText);
        newsObject.setMainPhoto(mainPhoto);
        newsObject.setVisibleInSearchEngines(visibleInSearchEngines);
        newsObject.setCategory(category);
        newsObject.setVisibility(visibility);
        newsObject.setCommentsRights(commentsRights);
        return newsObject;
    }

    public NewsObjectBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public NewsObjectBuilder withHtmlText(String text) {
        this.htmlText = String.format(TEMPLATE, text);
        return this;
    }

    public NewsObjectBuilder withId(Long id) {
        this.id = id;
        return this;
    }
}
