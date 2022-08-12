package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.dto.AmenityAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.repository.AmenityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AmenityService {

    private final AmenityRepository amenitiesRepository;
    private final ModelMapper modelMapper;


    public AmenityService(AmenityRepository amenitiesRepository, ModelMapper modelMapper) {
        this.amenitiesRepository = amenitiesRepository;
        this.modelMapper = modelMapper;
    }


    public void addAmenity(AmenityAddDTO amenityAddDTO) {
        Amenity amenity = modelMapper.map(amenityAddDTO, Amenity.class);

        amenitiesRepository.save(amenity);
    }
}
