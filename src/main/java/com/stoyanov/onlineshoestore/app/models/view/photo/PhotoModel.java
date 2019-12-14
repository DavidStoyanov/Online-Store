package com.stoyanov.onlineshoestore.app.models.view.photo;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoModel {

    @Expose
    private String name;
    @Expose
    private String imageUrl;
    @Expose
    private Integer position;
}
