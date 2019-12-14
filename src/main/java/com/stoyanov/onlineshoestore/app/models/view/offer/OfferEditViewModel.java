package com.stoyanov.onlineshoestore.app.models.view.offer;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OfferEditViewModel {

    private String id;
    private String title;
    private String description;
    private Condition condition;
    private BigDecimal price;
    private Set<Photo> photos;

    private ShoeType type;
    private List<ShoeSize> sizes;
}
