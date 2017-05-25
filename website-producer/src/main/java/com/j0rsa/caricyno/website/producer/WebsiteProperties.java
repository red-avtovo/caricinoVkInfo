package com.j0rsa.caricyno.website.producer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author red
 * @since 21.05.17
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "website")
@Data
public class WebsiteProperties {
    private String domain;
    private String url;
    private String username;
    private String password;
}
