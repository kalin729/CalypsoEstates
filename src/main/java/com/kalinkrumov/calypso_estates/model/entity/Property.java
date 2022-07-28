package com.kalinkrumov.calypso_estates.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Property {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private double area;

    private BigDecimal price;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany
    private List<Extra> extras;

    @OneToMany
    private List<Image> images;

    private boolean isVisible;

    public Long getId() {
        return id;
    }

    public Property setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Property setTitle(String title) {
        this.title = title;
        return this;
    }

    public double getArea() {
        return area;
    }

    public Property setArea(double area) {
        this.area = area;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Property setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Property setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Property setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public Property setExtras(List<Extra> extras) {
        this.extras = extras;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public Property setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public Property setVisible(boolean visible) {
        isVisible = visible;
        return this;
    }
}
