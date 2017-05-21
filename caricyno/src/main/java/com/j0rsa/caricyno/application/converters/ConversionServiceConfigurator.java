package com.j0rsa.caricyno.application.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;

@Component
public class ConversionServiceConfigurator {
    @Autowired(required = false)
    Set<Converter> converters = Collections.emptySet();

    @Autowired
    ConverterRegistry converterRegistry;


    @PostConstruct
    public void afterPropertiesSet() {
        ConversionServiceFactory.registerConverters(converters, converterRegistry);
    }
}