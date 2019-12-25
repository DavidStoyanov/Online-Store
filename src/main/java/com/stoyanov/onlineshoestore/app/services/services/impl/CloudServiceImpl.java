package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.cloudinary.Cloudinary;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudServiceImpl implements CloudService {

    private final Cloudinary cloudinary;
    private final HashMap<String, Object> options;

    @Autowired
    public CloudServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
        this.options = new HashMap<>();
        this.initOptions();
    }

    private void initOptions() {
        //this.options.put("public_id", "photo");
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            File file = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            Map upload = this.cloudinary.uploader().upload(file, this.options);
            return upload.get("public_id").toString();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
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
            this.cloudinary.uploader().destroy(id, this.options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
