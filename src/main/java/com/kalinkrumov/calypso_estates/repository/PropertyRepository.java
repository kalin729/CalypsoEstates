package com.kalinkrumov.calypso_estates.repository;

import com.kalinkrumov.calypso_estates.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    List<Property> findAllByOrderByCreatedAtAsc();

    Property findBySlug(String slug);

    @Query(value = "SELECT * FROM properties ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Property> getThreeRandomProperties();


//    @Query(value = "SELECT p FROM Property p WHERE p.amenities = :amenities")
//    List<Property> findAllByAmenitiesEquals(@Param("amenities") Set<Amenity> amenities);
}
