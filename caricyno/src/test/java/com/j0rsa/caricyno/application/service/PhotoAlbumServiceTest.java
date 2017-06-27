package com.j0rsa.caricyno.application.service;

import com.j0rsa.caricyno.vk.PhotoAlbumService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoAlbumServiceTest {
    @Autowired
    private PhotoAlbumService photoAlbumService;

    @Test
    public void testFind() throws ClientException, ApiException {
        // given
        Integer ownerId = -1;
        Integer albumId = -7;

        // when
        GetResponse photos = photoAlbumService.findPhotos(ownerId, albumId);

        // then
        assertThat(photos.getCount()).isEqualTo(30);
        assertThat(photos.getItems()).hasSize(30);
    }
}
