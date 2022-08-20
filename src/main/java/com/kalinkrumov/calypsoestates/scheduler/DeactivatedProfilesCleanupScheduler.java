package com.kalinkrumov.calypsoestates.scheduler;

import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeactivatedProfilesCleanupScheduler {

    private final UserService userService;

    public DeactivatedProfilesCleanupScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupDeactivatedProfiles(){
        List<User> deactivatedUsers = userService.getDeactivatedUsers();
        for (User deactivatedUser : deactivatedUsers) {
            userService.deleteUser(deactivatedUser);
        }
    }

}
