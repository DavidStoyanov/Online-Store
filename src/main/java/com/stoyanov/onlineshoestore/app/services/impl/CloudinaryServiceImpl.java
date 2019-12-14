package com.stoyanov.onlineshoestore.app.services.impl;

import com.cloudinary.Cloudinary;
import com.stoyanov.onlineshoestore.app.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final HashMap<String, Object> options;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
        this.options = new HashMap<>();
        this.initOptions();
    }

    private void initOptions() {
        //this.options.put("public_id", "photo");
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        Map upload = this.cloudinary.uploader().upload(file, this.options);
        return upload.get("url").toString();
    }

    @Override
    public void destroy(String id) throws IOException {
        this.cloudinary.uploader().destroy(id, this.options);
    }
}
