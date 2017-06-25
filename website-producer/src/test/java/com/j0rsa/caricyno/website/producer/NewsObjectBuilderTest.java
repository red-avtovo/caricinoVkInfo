package com.j0rsa.caricyno.website.producer;

import com.j0rsa.caricyno.website.producer.enums.ArticleRights;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author red
 * @since 23.06.17
 */

public class NewsObjectBuilderTest {
    @Test
    public void aNewsObject() throws Exception {

//        System.setProperty("com.j0rsa.caricyno.website.producer.NewsPublisher.DEBUG", "true");

        final NewsObject newsObject = NewsObjectBuilder.aNewsObject()
                .withDefaultCategory()
                .withDefaultCommentsRights()
                .withDefaultTags()
                .withTitle("test1")
                .withHtmlText("test1")
                .buildNewsObject();

        WebsiteProperties properties = new WebsiteProperties();
        properties.setDomain("odnodolshiki2.ru");
        properties.setUrl("https://odnodolshiki2.ru");
        properties.setUsername("user");
        properties.setPassword("pass");

        newsObject.setVisibility(ArticleRights.OWNER);
//        newsObject.setMainPhoto(new URL("http://greatwoodsflooring.com/wp-content/uploads/2014/02/test.jpg"));
        final NewsPublisher newsPublisher = new NewsPublisher(properties, new AuthorizationModule(properties));
        Assert.assertTrue(newsPublisher.publish(newsObject));
    }

}