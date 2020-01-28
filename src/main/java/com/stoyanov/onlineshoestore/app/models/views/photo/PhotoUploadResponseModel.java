package com.stoyanov.onlineshoestore.app.models.views.photo;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoUploadResponseModel {

    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private String url;
}
