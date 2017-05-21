package com.j0rsa.caricyno.application;

import com.j0rsa.caricyno.application.DemoApplication;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Autowired
    private DemoApplication demoApplication;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetNews() throws ClientException, ApiException {
        GetResponse news = demoApplication.getNews();
        assertThat(news).isNotNull();
        assertThat(news.getItems()).hasSize(1);
    }
}
