package com.stoyanov.onlineshoestore.app.models.view.photo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhotoViewModel {

    private String name;
    private String imageUrl;
    private Integer position;
}
