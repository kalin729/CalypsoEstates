package com.kalinkrumov.calypso_estates.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    private String title;

    private String slug;

    private double area;

    private BigDecimal price;

    private String location;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String mainImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany
    private List<Amenity> amenities;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private int floor;

    private int baths;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    private boolean isVisible;

    public UUID getId() {
        return id;
    }

    public Property setId(UUID id) {
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

    public String getSlug() {
        return slug;
    }

    public Property setSlug(String slug) {
        this.slug = slug;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Property setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getMainImage() {
        return mainImage;
    }

    public Property setMainImage(String mainImage) {
        this.mainImage = mainImage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Property setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public Property setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Property setStatus(Status status) {
        this.status = status;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public Property setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getBaths() {
        return baths;
    }

    public Property setBaths(int baths) {
        this.baths = baths;
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
