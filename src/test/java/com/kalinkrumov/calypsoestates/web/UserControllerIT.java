package com.kalinkrumov.calypsoestates.web;

import com.kalinkrumov.calypsoestates.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "testuser2@example.com")
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("password", "topsecret")
                        .param("confirmPassword", "topsecret")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(emailService).sendRegistrationEmail("testuser2@example.com", "Test User");
    }

    @Test
    void testLoginPageShown() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testUserLogin() throws Exception {
        mockMvc.perform(post("/users/login")
                        .param("username", "user@calypsoestates.com")
                        .param("password", "user")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(
            username = "admin@calypsoestates.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testAllUsersPageShown() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-all"));
    }

    @Test
    void testUsersPageShown_Forbidden() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(
            username = "admin@calypsoestates.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testUserChangeRoles() throws Exception {
        mockMvc.perform(post("/users/edit_roles/{username}", "user@calypsoestates.com")
                        .param("userRoleChangeDTO", "MODERATOR", "USER")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));
    }

}
