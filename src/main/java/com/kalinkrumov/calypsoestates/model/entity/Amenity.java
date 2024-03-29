package com.kalinkrumov.calypsoestates.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "amenities")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", unique = true, nullable = false)
    private String description;

    @ManyToMany(mappedBy = "amenities", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public Amenity setProperties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }
}
