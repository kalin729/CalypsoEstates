package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.model.entity.UserRole;
import com.kalinkrumov.calypsoestates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypsoestates.model.user.AppUserDetails;
import com.kalinkrumov.calypsoestates.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTests {

    @Mock
    private UserRepository mockUserRepo;

    private AppUserDetailsService toTest;

    @BeforeEach
    void setUp() {
        toTest = new AppUserDetailsService(mockUserRepo);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        User testUser = new User()
                .setUsername("test@example.com")
                .setPassword("topsecret")
                .setFirstName("Test")
                .setLastName("Testov")
                .setActive(true)
                .setCreatedAt(LocalDateTime.now())
                .setRoles(List.of(
                        new UserRole().setUserRole(UserRoleEnum.USER),
                        new UserRole().setUserRole(UserRoleEnum.ADMIN)))
                .setActive(true);

        when(mockUserRepo.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        AppUserDetails userDetails = (AppUserDetails) toTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertEquals(testUser.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.isActive(), userDetails.isActive());

        var authorities = userDetails.getAuthorities();
        Assertions.assertEquals(2, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(), authoritiesIter.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(), authoritiesIter.next().getAuthority());

    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com"));

    }

}
