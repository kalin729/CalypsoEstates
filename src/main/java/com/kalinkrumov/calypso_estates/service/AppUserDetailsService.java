package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.entity.User;
import com.kalinkrumov.calypso_estates.model.entity.UserRole;
import com.kalinkrumov.calypso_estates.model.user.AppUserDetails;
import com.kalinkrumov.calypso_estates.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

//Returned as a Bean. No Service annotation.
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

    public AppUserDetails loadCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (AppUserDetails) loadUserByUsername(authentication.getName());
    }

    private UserDetails map(User user){

        return new AppUserDetails(
                user.getId(),
                user.getPassword(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream().map(this::map).toList());

    }

    private GrantedAuthority map(UserRole userRole){
        return new SimpleGrantedAuthority("ROLE_" + userRole.getUserRole().name());
    }

}
