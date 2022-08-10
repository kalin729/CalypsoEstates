package com.kalinkrumov.calypso_estates.repository;

import com.kalinkrumov.calypso_estates.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
