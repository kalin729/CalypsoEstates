package com.kalinkrumov.calypsoestates.web;

import com.kalinkrumov.calypsoestates.model.entity.Amenity;
import com.kalinkrumov.calypsoestates.model.entity.Message;
import com.kalinkrumov.calypsoestates.model.entity.Property;
import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User testUser;
    private User testModerator;
    private User testAdmin;
    private Property testProperty;
    private Amenity testAmenity;
    private Message testMessage;

    @BeforeEach
    void setUp() throws IOException {
        testUser = testDataUtils.createTestUser("user@example.com");
        testModerator = testDataUtils.createTestModerator("moderator@example.com");
        testAdmin = testDataUtils.createTestAdmin("admin@example.com");
        testProperty = testDataUtils.createTestProperty();
        testAmenity = testDataUtils.createTestAmenity();
        testMessage = testDataUtils.createTestMessage();
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testMessageSend() throws Exception {

        mockMvc.perform(post("/message/send")
                        .param("senderName", "Test Sender")
                        .param("email", "testsender@example.com")
                        .param("subject", "Test Subject")
                        .param("message", "TestMessage")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contacts"));
    }

    @Test
    @WithMockUser(
            username = "admin@example.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testMessagesViewAllAdmin() throws Exception {
        mockMvc.perform(get("/messages/all")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("message-all"));
    }

    @Test
    @WithMockUser(
            username = "user@example.com",
            roles = {"USER"}
    )
    void testMessagesViewAllUser_Forbidden() throws Exception {
        mockMvc.perform(get("/messages/all")
                        .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(
            username = "admin@example.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testMessageSinglePageView() throws Exception {
        mockMvc.perform(get("/message/view/{id}", testMessage.getId())
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("message-view"));
    }

    @Test
    @WithMockUser(
            username = "admin@example.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testMessageReply() throws Exception {

        mockMvc.perform(post("/message/reply/{id}", testMessage.getId())
                        .param("reply", "Message reply.")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/message/view/" + testMessage.getId()));
    }

}
