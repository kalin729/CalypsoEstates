package com.kalinkrumov.calypsoestates.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeProviderService {

    public LocalDateTime now(){
        return LocalDateTime.now();
    }

}
