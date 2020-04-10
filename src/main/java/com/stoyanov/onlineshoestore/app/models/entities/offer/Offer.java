package com.stoyanov.onlineshoestore.app.models.entities.offer;

import com.stoyanov.onlineshoestore.app.models.enums.Condition;
import com.stoyanov.onlineshoestore.app.models.entities.base.BaseEntity;
import com.stoyanov.onlineshoestore.app.models.entities.user.User;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    private String title;
    private Category category;
    private String description;
    private BigDecimal price;
    private Condition condition;
    private User createdBy;
    private Date createdOn;
    private Long views;
    private List<Photo> photos;
    private Set<OfferAttribute> attributes;

    public Offer() {
        this.views = 0L;
        this.attributes = new HashSet<>();
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "description", length = 65535, columnDefinition="TEXT", nullable = false)
    @Type(type = "text")
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

    @Column(name = "offer_condition", nullable = false)
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

    @Column(name = "views", nullable = false)
    public Long getViews() {
        return this.views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "offer", targetEntity = Photo.class)
    public List<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    }, mappedBy = "offer", targetEntity = OfferAttribute.class)
    public Set<OfferAttribute> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Set<OfferAttribute> attributes) {
        this.attributes = attributes;
    }
}
