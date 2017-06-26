package com.j0rsa.caricyno.application;

import org.junit.Test;

import static com.j0rsa.caricyno.application.Utils.wrapLinks;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UtilsTest {
    @Test
    public void testLinkWrapper() throws Exception {
        assertThat(wrapLinks("asd"))
                .isEqualTo("asd");
        assertThat(wrapLinks("http://test.com/"))
                .isEqualTo("<a href='http://test.com/'>http://test.com/</a>");
        assertThat(wrapLinks("asd http://test.com/ asd"))
                .isEqualTo("asd <a href='http://test.com/'>http://test.com/</a> asd");
        assertThat(wrapLinks("asd http://tass.ru/obschestvo/4265891 asd http://tass.ru/obschestvo/4265891"))
                .isEqualTo("asd <a href='http://tass.ru/obschestvo/4265891'>http://tass.ru/obschestvo/4265891</a> asd <a href='http://tass.ru/obschestvo/4265891'>http://tass.ru/obschestvo/4265891</a>");
        assertThat(wrapLinks("МЫ ДОЛЖНЫ БЫТЬ на этом митинге!!!\n" +
                "http://tass.ru/obschestvo/4265891 численность урезали до 1,5 тыс человек, тем не менее - это отличный способ быть увиденными. Заказаны дополнительные кепки и футболки наших традиционных цветов, новые растяжки и флаги! Прорабатываются различные идеи эпатажного появления для привлечения внимания СМИ.\n" +
                "Ждем ваших идей!"))
                .isEqualTo("МЫ ДОЛЖНЫ БЫТЬ на этом митинге!!!\n" +
                        "<a href='http://tass.ru/obschestvo/4265891'>http://tass.ru/obschestvo/4265891</a> численность урезали до 1,5 тыс человек, тем не менее - это отличный способ быть увиденными. Заказаны дополнительные кепки и футболки наших традиционных цветов, новые растяжки и флаги! Прорабатываются различные идеи эпатажного появления для привлечения внимания СМИ.\n" +
                        "Ждем ваших идей!");
        assertThat(wrapLinks("https://pp.userapi.com/c637528/v637528337/4d137/dlE0s37hJ7w.jpg"))
                .isEqualTo("<img src='https://pp.userapi.com/c637528/v637528337/4d137/dlE0s37hJ7w.jpg' alt=''>");

        assertThat(wrapLinks("https://www.youtube.com/watch?v=sRVSKaGT4Ss"))
                .isEqualTo("<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/sRVSKaGT4Ss\" frameborder=\"0\" allowfullscreen></iframe>");

    }

}