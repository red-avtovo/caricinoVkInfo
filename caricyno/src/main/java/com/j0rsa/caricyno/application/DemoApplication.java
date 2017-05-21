package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.vk.NewsService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.j0rsa.caricyno.*")
public class DemoApplication {
    @Autowired
    private NewsService newsService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public GetResponse getNews() throws ClientException, ApiException {
        GetResponse response = newsService.getData();
        return response;
    }
}
