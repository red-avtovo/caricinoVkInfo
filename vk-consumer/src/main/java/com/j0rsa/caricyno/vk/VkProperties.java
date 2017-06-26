package com.j0rsa.caricyno.vk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "vk")
@Data
public class VkProperties {
    private String link;
    private Integer group;
    private Integer client;
    private String secret;
    private String service;
}
