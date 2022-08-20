package com.kalinkrumov.calypso_estates.model.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.thymeleaf.standard.expression.Each;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    private double area;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String location;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String mainImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "properties_amenities",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenity> amenities;

    @ManyToOne()
    private Status status;

    @Column(nullable = false)
    private int floor;

    @Column(nullable = false)
    private int baths;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    private boolean active;

    public void addAmenity(Amenity amenity){
        this.amenities.add(amenity);
//        amenity.getProperties().add(this);
    }

    public void removeAmenity(Amenity amenity){
        this.amenities.remove(amenity);
//        amenity.getProperties().remove(this);
    }

    public void addImage(Image image){
        this.images.add(image);
//        amenity.getProperties().add(this);
    }

    public void removeImage(Image image){
        this.images.remove(image);
//        amenity.getProperties().remove(this);
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public Property setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
        return this;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
