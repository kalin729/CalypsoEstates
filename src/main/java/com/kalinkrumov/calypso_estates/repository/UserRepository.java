package com.kalinkrumov.calypso_estates.repository;

import com.kalinkrumov.calypso_estates.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //this might be broken, it should be by username but we'll try to find by username or email
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndEmail(String username, String email);
}
