package com.stoyanov.onlineshoestore.app.models.services.offer;

import com.stoyanov.onlineshoestore.app.models.entities.user.User;
import com.stoyanov.onlineshoestore.app.models.enums.Condition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OfferServiceModel {

    private String id;
    private String title;
    private String categoryId;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private User createdBy;
    private Date createdOn;
    private List<PhotoServiceModel> photos;
    private Map<String, String> attributes;
}
