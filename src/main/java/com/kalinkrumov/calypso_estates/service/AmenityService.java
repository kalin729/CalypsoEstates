package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.dto.AmenityAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.repository.AmenityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

    public List<Amenity> getAllAmenities() {
        return amenitiesRepository.findAll();
    }

    public Amenity findById(String amenityId) {
        return amenitiesRepository.findById(Long.parseLong(amenityId)).orElseThrow(() -> new EntityNotFoundException("Amenity not found."));
    }
}
