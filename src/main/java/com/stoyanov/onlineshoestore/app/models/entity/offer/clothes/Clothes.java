package com.stoyanov.onlineshoestore.app.models.entity.offer.clothes;

import com.stoyanov.onlineshoestore.app.models.entity.offer.BaseOffer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clothes")
public class Clothes extends BaseOffer {

    public Clothes() {
    }
}
