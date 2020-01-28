package com.stoyanov.onlineshoestore.app.models.entities.offer;

import com.stoyanov.onlineshoestore.app.models.entities.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "product_attribute", uniqueConstraints =
    @UniqueConstraint(columnNames={"offer_id", "attribute_id"})
)
public class OfferAttribute extends BaseEntity {

    private Offer offer;
    private Attribute attribute;
    private String value;

    public OfferAttribute() {
    }

    public OfferAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public OfferAttribute(Attribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", referencedColumnName = "id")
    public Offer getOffer() {
        return this.offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id")
    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    @Column(name = "value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
