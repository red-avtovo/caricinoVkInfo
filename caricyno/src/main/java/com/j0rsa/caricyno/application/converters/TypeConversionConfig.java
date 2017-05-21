package com.j0rsa.caricyno.application.converters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@SuppressWarnings("unused")
@Configuration
public class TypeConversionConfig {

    @Primary
    @Bean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }
}
