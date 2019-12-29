package com.stoyanov.onlineshoestore.app.models.entity.offer;

import com.stoyanov.onlineshoestore.app.models.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    private String name;
    private List<BaseOffer> offers;

    public Category() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    public List<BaseOffer> getOffers() {
        return this.offers;
    }

    public void setOffers(List<BaseOffer> offers) {
        this.offers = offers;
    }
}
