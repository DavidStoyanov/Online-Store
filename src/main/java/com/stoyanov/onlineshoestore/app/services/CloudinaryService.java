package com.stoyanov.onlineshoestore.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String upload(MultipartFile multipartFile) throws IOException;

    void destroy(String id) throws IOException;
}
