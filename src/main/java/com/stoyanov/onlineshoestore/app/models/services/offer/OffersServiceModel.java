package com.stoyanov.onlineshoestore.app.models.services.offer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OffersServiceModel {

    private String id;
    private String title;
    private BigDecimal price;
    private Date createdOn;
    private Long views;
    private PhotoServiceModel photo;
}
