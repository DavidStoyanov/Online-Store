package com.stoyanov.onlineshoestore.app.models.view.photo;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponse {

    @Expose
    private String name;

    @Expose
    private String url;
}
