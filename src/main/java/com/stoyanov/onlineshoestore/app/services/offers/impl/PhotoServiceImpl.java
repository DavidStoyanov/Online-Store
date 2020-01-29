package com.stoyanov.onlineshoestore.app.services.offers.impl;

import com.google.gson.Gson;
import com.stoyanov.onlineshoestore.app.models.views.photo.PhotoUploadResponseModel;
import com.stoyanov.onlineshoestore.app.services.offers.PhotoService;
import com.stoyanov.onlineshoestore.app.services.services.CloudinaryService;
import com.stoyanov.onlineshoestore.app.services.services.PCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final CloudinaryService cloudinaryService;

    private final Gson gson;

    @Autowired
    public PhotoServiceImpl(CloudinaryService cloudinaryService, Gson gson) {
        this.cloudinaryService = cloudinaryService;
        this.gson = gson;
    }

    @Override
    public String upload(MultipartFile photo) {
        PhotoUploadResponseModel responseModel = this.cloudinaryService.upload(photo);
        List<PhotoUploadResponseModel> responseModels = new ArrayList<>();
        responseModels.add(responseModel);
        return this.gson.toJson(responseModels);
    }

    @Override
    public String upload(List<MultipartFile> photos) {
        List<PhotoUploadResponseModel> responseModels = this.cloudinaryService.upload(photos);
        return this.gson.toJson(responseModels);
    }

    @Override
    public void destroy(String photoId) {
        this.cloudinaryService.destroy(photoId);
    }
}
