package com.stoyanov.onlineshoestore.app.models.entity.offer;

import com.stoyanov.onlineshoestore.app.models.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo extends BaseEntity {

    private String name;
    private String imageUrl;
    private Integer position;
    private BaseOffer offer;

    public Photo() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "position", nullable = false)
    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    public BaseOffer getOffer() {
        return this.offer;
    }

    public void setOffer(BaseOffer offer) {
        this.offer = offer;
    }
}
