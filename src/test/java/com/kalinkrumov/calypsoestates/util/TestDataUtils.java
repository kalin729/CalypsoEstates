package com.kalinkrumov.calypsoestates.util;

import com.kalinkrumov.calypsoestates.model.entity.*;
import com.kalinkrumov.calypsoestates.model.enums.StatusEnum;
import com.kalinkrumov.calypsoestates.model.enums.UserRoleEnum;
import com.kalinkrumov.calypsoestates.repository.*;
import com.kalinkrumov.calypsoestates.service.DateTimeProviderService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class TestDataUtils {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PropertyRepository propertyRepository;
    private AmenityRepository amenityRepository;
    private StatusRepository statusRepository;
    private MessageRepository messageRepository;
    private DateTimeProviderService dateTimeProviderService;

    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository, PropertyRepository propertyRepository, AmenityRepository amenityRepository, StatusRepository statusRepository, MessageRepository messageRepository, DateTimeProviderService dateTimeProviderService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.propertyRepository = propertyRepository;
        this.amenityRepository = amenityRepository;
        this.statusRepository = statusRepository;
        this.messageRepository = messageRepository;
        this.dateTimeProviderService = dateTimeProviderService;
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole userRole = new UserRole().setUserRole(UserRoleEnum.USER);
            UserRole moderatorRole = new UserRole().setUserRole(UserRoleEnum.MODERATOR);
            UserRole adminRole = new UserRole().setUserRole(UserRoleEnum.ADMIN);

            userRoleRepository.save(userRole);
            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }

    public User createTestAdmin(String email) {
        User admin = new User()
                .setUsername(email)
                .setPassword("topsecret")
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setActive(true)
                .setRoles(userRoleRepository.findAll())
                .setCreatedAt(dateTimeProviderService.now());

        return userRepository.save(admin);
    }

    public User createTestModerator(String email) {
        User admin = new User()
                .setUsername(email)
                .setPassword("topsecret")
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setActive(true)
                .setCreatedAt(dateTimeProviderService.now())
                .setRoles(userRoleRepository.findAll().stream()
                        .filter(r -> r.getUserRole() == UserRoleEnum.MODERATOR)
                        .toList());

        return userRepository.save(admin);
    }

    public User createTestUser(String email) {
        User admin = new User()
                .setUsername(email)
                .setPassword("topsecret")
                .setFirstName("User")
                .setLastName("Userov")
                .setActive(true)
                .setCreatedAt(dateTimeProviderService.now())
                .setRoles(userRoleRepository.findAll().stream()
                        .filter(r -> r.getUserRole() == UserRoleEnum.USER)
                        .toList());

        return userRepository.save(admin);
    }

    public Message createTestMessage(){
        Message message = new Message()
                .setEmail("testuser@example.com")
                .setSubject("Test Subject")
                .setSenderName("Test Sender")
                .setProperty(null)
                .setMessage("Test message text.")
                .setCreatedAt(dateTimeProviderService.now())
                .setReplied(false);

        return messageRepository.save(message);
    }

    public Property createTestProperty() throws IOException {

        String filename = UUID.randomUUID() + ".png";

        Property property = new Property()
                .setTitle("Test")
                .setSlug("test")
                .setArea(100)
                .setImages(List.of(new Image().setImageUrl(filename)))
                .setMainImage(filename)
                .setPrice(BigDecimal.valueOf(50000))
                .setLocation("Burgas")
                .setStatus(statusRepository.findByStatus(StatusEnum.SALE))
                .setAmenities(null)
                .setFloor(2)
                .setBaths(1)
                .setDescription("TEST DESCRIPTION")
                .setCreatedAt(dateTimeProviderService.now());

        return propertyRepository.save(property);
    }

    public Amenity createTestAmenity(){
        Amenity amenity = new Amenity().setDescription("Washing Machine.");

        return amenityRepository.save(amenity);
    }

    public void cleanUpDatabase(){
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        amenityRepository.deleteAll();
        propertyRepository.deleteAll();
    }
}
