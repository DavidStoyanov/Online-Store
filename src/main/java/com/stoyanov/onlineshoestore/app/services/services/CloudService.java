package com.stoyanov.onlineshoestore.app.services.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudService {

    String upload(MultipartFile file);

    List<String> upload(List<MultipartFile> files);

    void destroy(String id);
}
