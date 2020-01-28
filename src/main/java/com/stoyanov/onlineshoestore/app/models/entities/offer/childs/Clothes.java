package com.stoyanov.onlineshoestore.app.models.entities.offer.childs;

public class Clothes {

    private String matter;

    public Clothes() {
    }

    /*@Column(name = "matter", nullable = false)*/
    public String getMatter() {
        return this.matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }
}
