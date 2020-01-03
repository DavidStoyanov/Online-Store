package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.google.gson.Gson;
import com.stoyanov.onlineshoestore.app.models.view.photo.PhotoResponse;
import com.stoyanov.onlineshoestore.app.services.offers.PhotoService;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final CloudService cloudService;

    private final Gson gson;

    public PhotoServiceImpl(@Qualifier("PCloudService") CloudService cloudService, Gson gson) {
        this.cloudService = cloudService;
        this.gson = gson;
    }

    @Override
    public String upload(MultipartFile photo) {
        String url = this.cloudService.upload(photo);
        List<PhotoResponse> photos = new ArrayList<>();
        PhotoResponse photoResponse = new PhotoResponse("photo", url);
        photos.add(photoResponse);
        return this.gson.toJson(photos);
    }

    @Override
    public List<String> upload(List<MultipartFile> photos) {
        return this.cloudService.upload(photos);
    }
}
