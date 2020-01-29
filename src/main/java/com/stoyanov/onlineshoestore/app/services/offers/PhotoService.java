package com.stoyanov.onlineshoestore.app.services.offers;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    String upload(MultipartFile photo);

    String upload(List<MultipartFile> photos);

    void destroy(String photoId);
}
