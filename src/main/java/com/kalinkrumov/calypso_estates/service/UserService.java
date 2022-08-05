package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.entity.User;
import com.kalinkrumov.calypso_estates.model.entity.UserRole;
import com.kalinkrumov.calypso_estates.model.dto.UserRegisterDTO;
import com.kalinkrumov.calypso_estates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypso_estates.repository.UserRepository;
import com.kalinkrumov.calypso_estates.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserDetailsService appUserDetailsService;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserDetailsService appUserDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.appUserDetailsService = appUserDetailsService;
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
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

    private void initAdmin(List<UserRole> roles) {
        User admin = new User()
                .setRoles(roles)
                .setFirstName("Admin")
                .setLastName("Adminov")
//                .setEmail("admin@admin.admin")
                .setUsername("admin@calypsoestates.com")
                .setPassword(passwordEncoder.encode("admin"));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRole> roles) {
        User moderator = new User()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
//                .setEmail("moderator@admin.admin")
                .setUsername("moderator@calypsoestates.com")
                .setPassword(passwordEncoder.encode("moderator"));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRole> roles) {
        User user = new User()
                .setRoles(roles)
                .setFirstName("User")
                .setLastName("Userov")
//                .setEmail("user@admin.admin")
                .setUsername("user@calypsoestates.com")
                .setPassword(passwordEncoder.encode("user"));

        userRepository.save(user);
    }

    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        Optional<User> found = userRepository.findByUsername(userRegisterDTO.getUsername());

        if (found.isPresent()) {
            return false;
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return false;
        }

        UserRole userRole = userRoleRepository.getUserRoleByUserRoleEquals(UserRoleEnum.USER);
        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRoles(List.of(userRole));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
