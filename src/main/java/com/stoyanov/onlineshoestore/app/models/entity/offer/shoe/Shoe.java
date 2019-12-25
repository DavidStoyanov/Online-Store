package com.stoyanov.onlineshoestore.app.models.entity.offer.shoe;

import com.stoyanov.onlineshoestore.app.models.entity.offer.BaseOffer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shoes")
public class Shoe extends BaseOffer {

    private ShoeType type;
    private List<ShoeSize> sizes;

    public Shoe() {
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public ShoeType getType() {
        return this.type;
    }

    public void setType(ShoeType type) {
        this.type = type;
    }

    @Column(name = "sizes")
    @ElementCollection(targetClass = ShoeSize.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "shoe_sizes",
            joinColumns = @JoinColumn(name = "shoe_id"))
    public List<ShoeSize> getSizes() {
        return this.sizes;
    }

    public void setSizes(List<ShoeSize> sizes) {
        this.sizes = sizes;
    }
}
