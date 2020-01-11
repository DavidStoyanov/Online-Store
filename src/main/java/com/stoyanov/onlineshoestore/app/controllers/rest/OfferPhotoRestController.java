package com.stoyanov.onlineshoestore.app.controllers.rest;

import com.stoyanov.onlineshoestore.app.services.offers.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class OfferPhotoRestController {

    private final PhotoService photoService;

    @Autowired
    public OfferPhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/photo/upload", headers = "Content-Type=multipart/form-data")
    public String uploadPhotos(@RequestParam("file") MultipartFile multipartFile) {
        String response = photoService.upload(multipartFile);
        return response;
    }

    @PostMapping("/photo/destroy")
    public void destroyPhoto(@RequestParam("id") Long photoId) {
        this.photoService.destroy(photoId);
    }
}
