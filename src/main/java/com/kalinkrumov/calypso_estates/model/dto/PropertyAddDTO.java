package com.kalinkrumov.calypso_estates.model.dto;

import com.kalinkrumov.calypso_estates.model.entity.Image;

import java.math.BigDecimal;
import java.util.List;

public class PropertyAddDTO {

    private String title;

    private String slug;

    private double area;

    private BigDecimal price;

    private String description;

    private String location;

    public String getLocation() {
        return location;
    }

    public PropertyAddDTO setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PropertyAddDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public PropertyAddDTO setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public double getArea() {
        return area;
    }

    public PropertyAddDTO setArea(double area) {
        this.area = area;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PropertyAddDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PropertyAddDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
