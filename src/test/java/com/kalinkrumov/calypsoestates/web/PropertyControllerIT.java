package com.kalinkrumov.calypsoestates.web;

import com.kalinkrumov.calypsoestates.model.entity.Amenity;
import com.kalinkrumov.calypsoestates.model.entity.Property;
import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.util.TestDataUtils;
import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User testUser;
    private User testModerator;
    private User testAdmin;
    private Property testProperty;
    private Amenity testAmenity;

    @BeforeEach
    void setUp() throws IOException {
        testUser = testDataUtils.createTestUser("user@example.com");
        testModerator = testDataUtils.createTestModerator("moderator@example.com");
        testAdmin = testDataUtils.createTestAdmin("admin@example.com");
        testProperty = testDataUtils.createTestProperty();
        testAmenity = testDataUtils.createTestAmenity();
        testAmenity = testDataUtils.createTestAmenity();
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithMockUser(
            username = "user@example.com",
            roles = {"USER"}
    )
    void testDeleteByUser_Forbidden() throws Exception {
        mockMvc.perform(get("/properties/delete/{slug}", testProperty.getSlug()).
                        with(csrf())
                ).
                andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(
            username = "moderator@example.com",
            roles = {"MODERATOR", "USER"}
    )
    void testDeleteByModerator() throws Exception {
        mockMvc.perform(get("/properties/delete/{slug}", testProperty.getSlug()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/properties/all"));
    }

    @Test
    @WithMockUser(
            username = "admin@example.com",
            roles = {"ADMIN", "MODERATOR", "USER"}
    )
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(get("/properties/delete/{slug}", testProperty.getSlug()).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/properties/all"));
    }

    @WithMockUser(
            username = "user@example.com",
            roles = {"USER"}
    )
    @Test
    void testAddPropertyUser_Forbidden() throws Exception {

        String filename = UUID.randomUUID() + ".png";

        MockMultipartFile firstFile = new MockMultipartFile("images", filename, "image/png", "some xml".getBytes());

        mockMvc.perform(multipart("/properties/add")
                        .file(firstFile)
                        .param("title", "tttesttt")
                        .param("area", "100")
                        .param("price", "5000")
                        .param("location", "Sofia")
                        .param("status", "SALE")
                        .param("floor", "1")
                        .param("baths", "1")
                        .param("active", "true")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "moderator@example.com",
            roles = {"MODERATOR", "USER"}
    )
    @Test
    void testAddPropertyModerator() throws Exception {

        String filename = UUID.randomUUID() + ".png";

        MockMultipartFile firstFile = new MockMultipartFile("images", filename, "image/png", "some xml".getBytes());

        mockMvc.perform(multipart("/properties/add")
                        .file(firstFile)
                        .param("title", "tttesttt")
                        .param("area", "100")
                        .param("price", "5000")
                        .param("location", "Sofia")
                        .param("status", "SALE")
                        .param("floor", "1")
                        .param("baths", "1")
                        .param("active", "true")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
