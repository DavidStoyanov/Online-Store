package com.stoyanov.onlineshoestore.app.web.controllers.rest;

import com.stoyanov.onlineshoestore.app.models.views.photo.PhotoUploadResponseModel;
import com.stoyanov.onlineshoestore.app.services.offers.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class OfferPhotoRestController {

    private final PhotoService photoService;

    @Autowired
    public OfferPhotoRestController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/photo/upload", headers = "Content-Type=multipart/form-data")
    public String uploadPhotos(@RequestParam("file") MultipartFile multipartFile) {
        return photoService.upload(multipartFile);
    }

    @PostMapping("/photo/destroy")
    public void destroyPhoto(@RequestParam("id") String photoId) {
        this.photoService.destroy(photoId);
    }
}
