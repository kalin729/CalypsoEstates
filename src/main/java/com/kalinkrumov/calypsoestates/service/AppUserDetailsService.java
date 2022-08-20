package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.model.entity.UserRole;
import com.kalinkrumov.calypsoestates.model.user.AppUserDetails;
import com.kalinkrumov.calypsoestates.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with this username/email."));
    }

    private UserDetails map(User user){

        return new AppUserDetails(
                user.getId(),
                user.getPassword(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream().map(this::map).toList(),
                user.isActive());

    }

    private GrantedAuthority map(UserRole userRole){
        return new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole().name());
    }

}
