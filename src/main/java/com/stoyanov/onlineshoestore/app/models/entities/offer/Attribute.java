package com.stoyanov.onlineshoestore.app.models.entities.offer;

import com.stoyanov.onlineshoestore.app.models.entities.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "attribute")
public class Attribute extends BaseEntity {

    private String name;

    public Attribute() {
    }

    public Attribute(String name) {
        this.name = name;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
