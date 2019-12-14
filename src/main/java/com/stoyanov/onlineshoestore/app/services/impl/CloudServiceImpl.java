package com.stoyanov.onlineshoestore.app.services.impl;

import com.stoyanov.onlineshoestore.app.services.CloudService;
import com.stoyanov.onlineshoestore.app.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloudServiceImpl implements CloudService {

    private final CloudinaryService cloudinaryService;

    @Autowired
    public CloudServiceImpl(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public String upload(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return this.cloudinaryService.upload(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<String> upload(List<MultipartFile> files) {
        List<String> urlList = new ArrayList<>();
        files.forEach(file -> {
            String url = this.upload(file);
            urlList.add(url);
        });

        return urlList;
    }

    @Override
    public void destroy(String id) {
        try {
            this.cloudinaryService.destroy(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
