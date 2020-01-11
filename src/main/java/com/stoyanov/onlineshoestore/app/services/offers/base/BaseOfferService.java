package com.stoyanov.onlineshoestore.app.services.offers.base;

import com.stoyanov.onlineshoestore.app.models.entity.offer.BaseOffer;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.services.CloudinaryService;
import com.stoyanov.onlineshoestore.app.services.services.PCloudService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseOfferService {

    private final CloudinaryService cloudinaryService;

    public BaseOfferService(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    protected String getCurrentAuthUsername() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return authentication.getName();
    }

    protected void destroyPhotos(BaseOffer offer) {
        offer.getPhotos().forEach(photo -> this.cloudinaryService.destroy(photo.getImageUrl()));
    }

    protected void uploadPhotos(BaseOffer offer, List<MultipartFile> filePhotos) {
        List<Photo> photos = filePhotos.stream()
                .map(file -> {
                    String imageUrl = this.cloudinaryService.upload(file);
                    Photo photo = new Photo();
                    photo.setImageUrl(imageUrl);
                    photo.setName(file.getOriginalFilename());
                    photo.setOffer(offer);

                    return photo;
                })
                .collect(Collectors.toList());

        offer.setPhotos(photos);
    }
}
