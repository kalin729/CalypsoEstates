package com.kalinkrumov.calypsoestates.repository;

import com.kalinkrumov.calypsoestates.model.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    List<Property> findAllByOrderByCreatedAtAsc();

    Property findBySlug(String slug);

    @Query(value = "SELECT * FROM properties WHERE active = true ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Property> getThreeRandomProperties();

    Page<Property> findAllByActive(Pageable pageable, boolean active);
}
