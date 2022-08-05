package com.kalinkrumov.calypso_estates.repository;

import com.kalinkrumov.calypso_estates.model.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    List<Property> findAllByOrderByCreatedAtAsc();

    Property findBySlug(String slug);
}
