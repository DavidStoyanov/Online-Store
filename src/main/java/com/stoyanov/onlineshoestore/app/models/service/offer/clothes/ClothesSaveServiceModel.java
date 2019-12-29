package com.stoyanov.onlineshoestore.app.models.service.offer.clothes;

import com.stoyanov.onlineshoestore.app.models.service.offer.base.OfferSaveServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClothesSaveServiceModel extends OfferSaveServiceModel {

    private String matter;
}
