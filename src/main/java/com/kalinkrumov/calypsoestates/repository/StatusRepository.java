package com.kalinkrumov.calypsoestates.repository;

import com.kalinkrumov.calypsoestates.model.entity.Status;
import com.kalinkrumov.calypsoestates.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatus(StatusEnum statusEnum);
}
