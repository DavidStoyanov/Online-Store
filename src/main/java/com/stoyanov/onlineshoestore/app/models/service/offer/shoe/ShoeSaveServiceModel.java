package com.stoyanov.onlineshoestore.app.models.service.offer.shoe;

import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeSize;
import com.stoyanov.onlineshoestore.app.models.entity.offer.shoe.ShoeType;
import com.stoyanov.onlineshoestore.app.models.service.offer.base.OfferSaveServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShoeSaveServiceModel extends OfferSaveServiceModel {

    private ShoeType type;
    private List<ShoeSize> sizes;
}
