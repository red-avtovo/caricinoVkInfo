package com.j0rsa.caricyno.website.producer;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author red
 * @since 07.05.17
 */

public class NewsPublisher {
    private static String websiteOrigin = "http://odnodolshiki2.ru";
    private static String articleAddUrl = websiteOrigin + "/articles/add";

    public boolean publish(NewsObject newsObject) throws IOException {
        final List<NameValuePair> formParams = makeFormParams(newsObject);
        return Request.Post(articleAddUrl)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Origin", websiteOrigin)
                .addHeader("Referer", articleAddUrl)
                .addHeader("Upgrade-Insecure-Requests", "1")
                .bodyForm(formParams)
                .execute()
        .returnResponse()
                .getStatusLine()
                .getStatusCode() == 200;

    }

    private ArrayList<NameValuePair> makeFormParams(NewsObject newsObject) {
        ArrayList<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("title", newsObject.getTitle()));
        formParams.add(new BasicNameValuePair("category_id",
                String.valueOf(newsObject.getCategory().getCategoryId())
                ));
        formParams.add(new BasicNameValuePair("MAX_FILE_SIZE", "268435456"));
        formParams.add(new BasicNameValuePair("art_photo", ""));
        formParams.add(new BasicNameValuePair("description", newsObject.getHtmlText()));
        formParams.add(new BasicNameValuePair("search", newsObject.isVisibleInSearchEngines()?"true":""));
        formParams.add(new BasicNameValuePair("tags_info", String.join(", ", newsObject.getTags())));
        formParams.add(new BasicNameValuePair("auth_view",
                newsObject.getVisibility().toString().toLowerCase())
        );
        formParams.add(new BasicNameValuePair("auth_comment",
                newsObject.getCommentsRights().toString().toLowerCase())
        );

        return formParams;
    }
}
