package com.stoyanov.onlineshoestore.app.models.services.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoServiceModel {

    private String id;
    private String name;
    private String url;
    private Integer position;
    private Integer degree;
}
