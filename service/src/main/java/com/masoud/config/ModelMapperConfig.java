package com.masoud.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;

@Configuration

public class ModelMapperConfig {
    @Bean
    public ModelMapper modelmapper()
    {
        return new ModelMapper();


    }

}
