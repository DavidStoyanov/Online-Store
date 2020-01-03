package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.pcloud.sdk.*;
import com.stoyanov.onlineshoestore.app.services.services.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("PCloudService")
public class PCloudServiceImpl implements CloudService {

    private final ApiClient apiClient;

    @Autowired
    public PCloudServiceImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }


    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            File localFile = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(localFile);

            RemoteFile uploadedFile = apiClient.createFile(
                    RemoteFolder.ROOT_FOLDER_ID,
                    localFile.getName(),
                    DataSource.create(localFile)
            ).execute();

            return uploadedFile.createFileLink().bestUrl().getPath();
        } catch (IOException | ApiError exception) {
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
//        try {
//            this.cloudinary.uploader().destroy(id, this.options);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
