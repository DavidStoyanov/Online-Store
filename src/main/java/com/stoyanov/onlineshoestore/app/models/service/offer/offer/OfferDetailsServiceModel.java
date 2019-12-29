package com.stoyanov.onlineshoestore.app.models.service.offer.offer;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.view.photo.PhotoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfferDetailsServiceModel {

    private String id;
    private String categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private List<PhotoModel> photos;
}
