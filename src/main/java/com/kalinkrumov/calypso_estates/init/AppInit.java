package com.kalinkrumov.calypso_estates.init;

import com.kalinkrumov.calypso_estates.service.PropertyService;
import com.kalinkrumov.calypso_estates.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final UserService userService;
    private final PropertyService propertyService;

    public AppInit(UserService userService, PropertyService propertyService) {
        this.userService = userService;
        this.propertyService = propertyService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
        propertyService.init();
    }
}
