package com.j0rsa.caricyno.application.converters;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author red
 * @since 26.05.17
 */

public class WallFullPostToPostConverterTest {

    @Test
    public void testLinkWrapper() throws Exception {
        assertThat(WallFullPostToPostConverter.wrapLinks("asd"))
                .isEqualTo("asd");
        assertThat(WallFullPostToPostConverter.wrapLinks("http://test.com/"))
                .isEqualTo("<a href='http://test.com/'>http://test.com/</a>");
        assertThat(WallFullPostToPostConverter.wrapLinks("asd http://test.com/ asd"))
                .isEqualTo("asd <a href='http://test.com/'>http://test.com/</a> asd");
    }

}