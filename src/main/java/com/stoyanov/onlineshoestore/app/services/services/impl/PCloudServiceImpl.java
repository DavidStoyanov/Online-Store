package com.stoyanov.onlineshoestore.app.services.services.impl;

import com.pcloud.sdk.*;
import com.stoyanov.onlineshoestore.app.models.views.photo.PhotoUploadResponseModel;
import com.stoyanov.onlineshoestore.app.services.services.PCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PCloudServiceImpl implements PCloudService {

    private final ApiClient apiClient;

    @Autowired
    public PCloudServiceImpl(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public PhotoUploadResponseModel upload(MultipartFile multipartFile) {
        PhotoUploadResponseModel photoResponse = null;

        try {
            File localFile = File.createTempFile("temp-file", multipartFile.getOriginalFilename());
            multipartFile.transferTo(localFile);

            RemoteFile uploadedFile = apiClient.createFile(
                    RemoteFolder.ROOT_FOLDER_ID,
                    localFile.getName(),
                    DataSource.create(localFile)
            ).execute();

            String name = multipartFile.getOriginalFilename();
            String url = uploadedFile.createFileLink().bestUrl().getPath().substring(1);
            Long id = uploadedFile.fileId();
            //photoResponse = new PhotoUploadResponseModel(id, name, url);
            return null;
        } catch (IOException | ApiError exception) {
            exception.printStackTrace();
        }

        return photoResponse;
    }

    @Override
    public List<PhotoUploadResponseModel> upload(List<MultipartFile> files) {
        List<PhotoUploadResponseModel> responseModels = new ArrayList<>();
        files.forEach(file -> {
            PhotoUploadResponseModel responseModel = this.upload(file);
            responseModels.add(responseModel);
        });

        return responseModels;
    }

    @Override
    public void destroy(Long id) {
        try {
            Boolean execute = this.apiClient.deleteFile(id).execute();
        } catch (IOException | ApiError e) {
            e.printStackTrace();
        }
    }
}
