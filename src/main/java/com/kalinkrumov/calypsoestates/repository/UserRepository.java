package com.kalinkrumov.calypsoestates.repository;

import com.kalinkrumov.calypsoestates.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //this might be broken, it should be by username but we'll try to find by username or email
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u FROM User u WHERE u.isActive = false")
    List<User> findAllDeactivated();
}
