package com.kalinkrumov.calypso_estates.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "amenities", fetch = FetchType.EAGER)
    Set<Property> properties;

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

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
}
