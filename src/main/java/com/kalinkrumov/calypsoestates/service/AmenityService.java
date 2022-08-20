package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.dto.AmenityAddDTO;
import com.kalinkrumov.calypsoestates.model.entity.Amenity;
import com.kalinkrumov.calypsoestates.repository.AmenityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AmenityService {

    private final AmenityRepository amenitiesRepository;
    private final PropertyService propertyService;
    private final ModelMapper modelMapper;


    public AmenityService(AmenityRepository amenitiesRepository, PropertyService propertyService, ModelMapper modelMapper) {
        this.amenitiesRepository = amenitiesRepository;
        this.propertyService = propertyService;
        this.modelMapper = modelMapper;
    }


    public void addAmenity(AmenityAddDTO amenityAddDTO) {
        Amenity amenity = modelMapper.map(amenityAddDTO, Amenity.class);

        amenitiesRepository.save(amenity);
    }

    public void editAmenity(Long id, AmenityAddDTO amenityAddDTO) {
        Amenity amenity = modelMapper.map(amenityAddDTO, Amenity.class);
        amenity.setId(id);

        amenitiesRepository.save(amenity);
    }

    public List<Amenity> getAllAmenities() {
        return amenitiesRepository.findAll();
    }

    public Amenity findById(Long amenityId) {
        return amenitiesRepository.findById(amenityId).orElseThrow(() -> new EntityNotFoundException("Amenity with this ID not found."));
    }

    public boolean deleteAmenity(Long id) {

        Amenity toDelete = this.findById(id);
        propertyService.removeAmenity(toDelete);

        amenitiesRepository.delete(toDelete);

        return true;
    }

    public AmenityAddDTO getAmenityDTOById(Long id) {
        return modelMapper.map(amenitiesRepository.findById(id).orElse(null), AmenityAddDTO.class);
    }
}
