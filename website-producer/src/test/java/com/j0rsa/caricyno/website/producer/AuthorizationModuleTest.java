package com.j0rsa.caricyno.website.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author red
 * @since 21.05.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationModuleTest {

    @Autowired
    private AuthorizationModule authorizationModule;

    @Test
    public void checkAuthorizeWhenAuthorized() throws Exception {
        final String authCookie = authorizationModule.authorize();
        assertThat(authorizationModule.isAuthorized(authCookie))
                .isTrue();
    }

    @Test
    public void checkAuthorizeWhenNotAuthorized() throws Exception {
        assertThat(authorizationModule.isAuthorized("notValidAuthSession"))
                .isFalse();
    }

}