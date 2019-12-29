package com.stoyanov.onlineshoestore.app.models.service.offer.clothes;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.view.photo.PhotoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClothesDetailsServiceModel {

    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private List<PhotoModel> photos;
    private String matter;
}
