package com.stoyanov.onlineshoestore.app.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Offer;
import com.stoyanov.onlineshoestore.app.models.entities.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.services.offer.OfferServiceModel;
import com.stoyanov.onlineshoestore.app.models.services.offer.PhotoServiceModel;
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

        PropertyMap<OfferServiceModel, Offer> offerSaveMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(null);
                map().setCategory(null);
                skip(destination.getAttributes());
                skip(destination.getCreatedBy());
                skip(destination.getCreatedOn());
                skip(destination.getPhotos());
                skip(destination.getViews());
            }

        };

        PropertyMap<PhotoServiceModel, Photo> photoModelPhotoMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setImageUrl(source.getUrl());
                map().setImageId(source.getId());
                skip(destination.getId());
            }
        };

        PropertyMap<Photo, PhotoServiceModel> photoPhotoModelMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setUrl(source.getImageUrl());
                map().setId(source.getImageId());
            }
        };


        modelMapper.addMappings(offerSaveMap);
        modelMapper.addMappings(photoModelPhotoMap);
        modelMapper.addMappings(photoPhotoModelMap);

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
