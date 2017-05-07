package com.j0rsa.caricyno.website.producer;

import com.j0rsa.caricyno.website.producer.enums.ArticleRights;
import com.j0rsa.caricyno.website.producer.enums.Category;
import lombok.Data;

import java.net.URL;
import java.util.List;

/**
 * @author red
 * @since 07.05.17
 */

@Data
public class NewsObject {
    private String title;
    private Category category;
    private URL mainPhoto;
    private String text;
    private boolean visibleInSearchEngines;
    private List<String> tags;
    private ArticleRights visibility;
    private ArticleRights commentsRights;
}
