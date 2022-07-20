package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.entity.User;
import com.kalinkrumov.calypso_estates.model.entity.UserRole;
import com.kalinkrumov.calypso_estates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypso_estates.repository.UserRepository;
import com.kalinkrumov.calypso_estates.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0){
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);
            UserRole moderatorRole = new UserRole().setUserRole(UserRoleEnum.MODERATOR);
            UserRole userRole = new UserRole().setUserRole(UserRoleEnum.USER);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);
            userRole = userRoleRepository.save(userRole);

            initAdmin(List.of(adminRole, moderatorRole, userRole));
            initModerator(List.of(moderatorRole, userRole));
            initUser(List.of(userRole));
        }
    }

    private void initAdmin(List<UserRole> roles){
        User admin = new User()
                .setRoles(roles)
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setEmail("admin@admin.admin")
                .setUsername("admin")
                .setPassword(passwordEncoder.encode("admin"));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRole> roles){
        User moderator = new User()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@admin.admin")
                .setUsername("moderator")
                .setPassword(passwordEncoder.encode("moderator"));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRole> roles){
        User user = new User()
                .setRoles(roles)
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@admin.admin")
                .setUsername("user")
                .setPassword(passwordEncoder.encode("user"));

        userRepository.save(user);
    }
}
