package com.stoyanov.onlineshoestore.app.controllers.rest;

import com.stoyanov.onlineshoestore.app.services.offers.PhotoService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
        //todo: upload photo, return url(JSON) save it to input with image data, show the photo in section
    }
}
