package com.kalinkrumov.calypso_estates.repository;

import com.kalinkrumov.calypso_estates.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
