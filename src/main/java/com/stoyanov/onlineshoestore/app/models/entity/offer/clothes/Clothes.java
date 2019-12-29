package com.stoyanov.onlineshoestore.app.models.entity.offer.clothes;

import com.stoyanov.onlineshoestore.app.models.entity.offer.BaseOffer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clothes")
public class Clothes extends BaseOffer {

    private String matter;

    public Clothes() {
    }

    @Column(name = "matter", nullable = false)
    public String getMatter() {
        return this.matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }
}
