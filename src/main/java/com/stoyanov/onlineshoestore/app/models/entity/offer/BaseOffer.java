package com.stoyanov.onlineshoestore.app.models.entity.offer;

import com.stoyanov.onlineshoestore.app.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entity.base.BaseEntity;
import com.stoyanov.onlineshoestore.app.models.entity.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "offers")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseOffer extends BaseEntity {

    private String title;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private User createdBy;
    private Date createdOn;
    private List<Photo> photos;

    public BaseOffer() {
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "offer_condition")
    @Enumerated(EnumType.STRING)
    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_on", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "offer", targetEntity = Photo.class)
    public List<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
