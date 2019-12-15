package com.stoyanov.onlineshoestore.app.models.view.offer;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entity.offer.Photo;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.view.photo.PhotoModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfferCreateViewModel {

    private String title;
    private String description;
    private Condition condition;
    private BigDecimal price;
    private List<MultipartFile> photos;

    private ShoeType type;
    private List<ShoeSize> sizes;
}
