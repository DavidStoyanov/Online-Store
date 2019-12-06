package com.stoyanov.onlineshoestore.app.models.entity.offers;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entity.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseOffer extends BaseEntity {

    private String title;
    private String description;
    private Condition condition;
    private BigDecimal price;

    public BaseOffer() {
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", precision = 8, scale = 2, nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "offer_condition")
    @Enumerated(EnumType.STRING)
    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
