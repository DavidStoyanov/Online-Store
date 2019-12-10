package com.stoyanov.onlineshoestore.app.models.entity.offers;

import com.stoyanov.onlineshoestore.app.enums.ShoeType;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shoes")
public class Shoe extends BaseOffer {

    private List<Integer> sizes;
    private ShoeType type;

    public Shoe() {
    }

    @Column(name = "sizes")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "shoes_sizes")
    public List<Integer> getSizes() {
        return this.sizes;
    }

    public void setSizes(List<Integer> sizes) {
        this.sizes = sizes;
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public ShoeType getType() {
        return this.type;
    }

    public void setType(ShoeType type) {
        this.type = type;
    }
}
