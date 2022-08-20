package com.kalinkrumov.calypsoestates.repository;

import com.kalinkrumov.calypsoestates.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findAllByOrderByCreatedAtDesc();

}
