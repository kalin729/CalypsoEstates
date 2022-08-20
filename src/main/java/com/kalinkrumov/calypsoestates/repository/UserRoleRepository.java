package com.kalinkrumov.calypsoestates.repository;

import com.kalinkrumov.calypsoestates.model.entity.UserRole;
import com.kalinkrumov.calypsoestates.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole getUserRoleByUserRoleEquals(UserRoleEnum userRole);

}
