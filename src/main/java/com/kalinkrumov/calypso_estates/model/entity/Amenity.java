package com.kalinkrumov.calypso_estates.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "amenities")
    List<Property> properties;

    public Long getId() {
        return id;
    }

    public Amenity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Amenity setDescription(String description) {
        this.description = description;
        return this;
    }
}
