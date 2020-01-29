package com.stoyanov.onlineshoestore.app.services.services;

import com.stoyanov.onlineshoestore.app.models.views.photo.PhotoUploadResponseModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {

    PhotoUploadResponseModel upload(MultipartFile file);

    List<PhotoUploadResponseModel> upload(List<MultipartFile> files);

    void destroy(String id);
}
