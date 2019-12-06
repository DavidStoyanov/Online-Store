package com.stoyanov.onlineshoestore.app.models.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    private String id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(
            name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
