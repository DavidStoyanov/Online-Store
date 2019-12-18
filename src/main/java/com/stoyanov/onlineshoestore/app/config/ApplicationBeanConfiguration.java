package com.stoyanov.onlineshoestore.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.Shoe;
import com.stoyanov.onlineshoestore.app.models.service.offer.shoe.ShoeEditServiceModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<ShoeEditServiceModel, Shoe> shoeMapping = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getPhotos());
            }
        };
        modelMapper.addMappings(shoeMapping);

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
