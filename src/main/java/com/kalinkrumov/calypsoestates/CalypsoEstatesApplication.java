package com.kalinkrumov.calypsoestates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CalypsoEstatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalypsoEstatesApplication.class, args);
    }

}
