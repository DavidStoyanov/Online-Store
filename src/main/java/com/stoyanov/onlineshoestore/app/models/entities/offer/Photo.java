package com.stoyanov.onlineshoestore.app.models.entities.offer;

import com.stoyanov.onlineshoestore.app.models.entities.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo extends BaseEntity {

    private String name;
    private String imageId;
    private String imageUrl;
    private Integer position;
    private Integer degree;
    private Offer offer;

    public Photo() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "image_id", nullable = false)
    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
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

    @Column(name = "degree", nullable = false)
    public Integer getDegree() {
        return this.degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
