package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.stoyanov.onlineshoestore.app.models.views.photo.PhotoUploadResponseModel;
import com.stoyanov.onlineshoestore.app.services.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public PhotoUploadResponseModel upload(MultipartFile multipartFile) {
        PhotoUploadResponseModel photoResponse = null;

        try {
            File file = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            Map upload = this.cloudinary.uploader().upload(file, this.options);

            String id = upload.get("public_id").toString();
            String name = upload.get("original_filename").toString();
            String url = upload.get("url").toString();
            String format = upload.get("format").toString();

            return new PhotoUploadResponseModel(id, name, url, format);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PhotoUploadResponseModel> upload(List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .collect(Collectors.toList());
    }

    @Override
    public void destroy(String id) {
        try {
            this.cloudinary.uploader().destroy(id, this.options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getShortUrl(String str) {
        final String regex = "^.*?cloudinary.com/.*?/image/upload/.*?/(.*).jpg$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? matcher.group(1) : "";
    }
}
