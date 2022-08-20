package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.dto.UserRegisterDTO;
import com.kalinkrumov.calypsoestates.model.dto.UserRoleChangeDTO;
import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.model.entity.UserRole;
import com.kalinkrumov.calypsoestates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypsoestates.repository.UserRepository;
import com.kalinkrumov.calypsoestates.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserDetailsService appUserDetailsService;
    private final EmailService emailService;
    private final DateTimeProviderService dateTimeProviderService;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper,
                       UserDetailsService appUserDetailsService,
                       EmailService emailService,
                       DateTimeProviderService dateTimeProviderService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.appUserDetailsService = appUserDetailsService;
        this.emailService = emailService;
        this.dateTimeProviderService = dateTimeProviderService;
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
                .setUsername("admin@calypsoestates.com")
                .setCreatedAt(dateTimeProviderService.now())
                .setPassword(passwordEncoder.encode("admin"));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRole> roles) {
        User moderator = new User()
                .setRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setUsername("moderator@calypsoestates.com")
                .setCreatedAt(dateTimeProviderService.now())
                .setPassword(passwordEncoder.encode("moderator"));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRole> roles) {
        User user = new User()
                .setRoles(roles)
                .setFirstName("User")
                .setLastName("Userov")
                .setUsername("user@calypsoestates.com")
                .setCreatedAt(dateTimeProviderService.now())
                .setPassword(passwordEncoder.encode("user"));

        userRepository.save(user);
    }

    public List<User> getDeactivatedUsers(){

        return userRepository.findAllDeactivated();
    }

    public void deleteUser(User user){
        userRepository.delete(user);
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
        user.setCreatedAt(dateTimeProviderService.now());
        user.setActive(true);
        userRepository.save(user);

        UserDetails userDetails = appUserDetailsService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        emailService.sendRegistrationEmail(user.getUsername(), user.getFirstName() + " " + user.getLastName());

        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserRole> getAllUserRoles(){
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(userRoleRepository.getUserRoleByUserRoleEquals(UserRoleEnum.USER));
        userRoles.add(userRoleRepository.getUserRoleByUserRoleEquals(UserRoleEnum.MODERATOR));
        userRoles.add(userRoleRepository.getUserRoleByUserRoleEquals(UserRoleEnum.ADMIN));

        return userRoles;
    }

    public void changeUserRoles(String username, UserRoleChangeDTO userRoleChangeDTO){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with this username/email."));
        user.setRoles(userRoleChangeDTO.getRoles());

        userRepository.save(user);
    }

    public void changeUserActiveStatus(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with this username/email."));
        user.setActive(!user.isActive());

        userRepository.save(user);
    }
}
