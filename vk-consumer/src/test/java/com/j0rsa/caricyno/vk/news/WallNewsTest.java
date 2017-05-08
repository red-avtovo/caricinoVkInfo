package com.j0rsa.caricyno.vk.news;

import com.j0rsa.caricyno.vk.AppConfig;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WallNewsTest {

    @Test
    public void getLastOwnerRecord() throws Exception {
        AppConfig.main(null);
        GetResponse lastResponse = AppConfig.lastResponse;
        assertThat(lastResponse).isNotNull();
        assertThat(lastResponse.getItems()).hasSize(1);
    }

}