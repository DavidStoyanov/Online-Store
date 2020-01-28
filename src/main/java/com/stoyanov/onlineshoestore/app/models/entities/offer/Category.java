package com.stoyanov.onlineshoestore.app.models.entities.offer;

import com.stoyanov.onlineshoestore.app.models.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    private String name;
    private List<Offer> offers;

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
    public List<Offer> getOffers() {
        return this.offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
