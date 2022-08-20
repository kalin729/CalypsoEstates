package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.repository.PropertyRepository;
import com.kalinkrumov.calypsoestates.repository.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTests {

    @Mock
    PropertyRepository propertyRepository;

    @Mock
    StatusRepository statusRepository;

    @Mock
    FileStorageService fileStorageService;

    @Mock
    ModelMapper modelMapper;

    PropertyService toTest;

    @BeforeEach
    void setUp(){
        toTest = new PropertyService(propertyRepository, statusRepository, fileStorageService, modelMapper);
    }

}
