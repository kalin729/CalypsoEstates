package com.kalinkrumov.calypso_estates.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.model.entity.Status;
import com.kalinkrumov.calypso_estates.model.enums.StatusEnum;
import com.kalinkrumov.calypso_estates.repository.PropertyRepository;
import com.kalinkrumov.calypso_estates.repository.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final StatusRepository statusRepository;
    private final ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, StatusRepository statusRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.statusRepository = statusRepository;
        this.modelMapper = modelMapper;
    }

    public void init() {
        if (propertyRepository.count() == 0 && statusRepository.count() == 0) {
            Status rentStatus = new Status().setStatus(StatusEnum.RENT);
            Status saleStatus = new Status().setStatus(StatusEnum.SALE);

            rentStatus = statusRepository.save(rentStatus);
            saleStatus = statusRepository.save(saleStatus);
        }
    }

    public void addProperty(PropertyAddDTO propertyAddDTO, List<Image> images) {
        Property property = modelMapper.map(propertyAddDTO, Property.class);
        property.setImages(images);
        property.setCreatedAt(LocalDateTime.now());
        property.setStatus(statusRepository.findByStatus(propertyAddDTO.getStatus()));
        propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAllByOrderByCreatedAtAsc();
    }

    public Property getPropertyBySlug(String slug) {
        return propertyRepository.findBySlug(slug);
    }

    public List<Property> getThreeRandomProperties() {

        return propertyRepository.getThreeRandomProperties();

    }

//    public List<Property> getPropertiesByAmenities(List<Amenity> amenities){
//        return propertyRepository.findAllByAmenitiesEquals(amenities);
//    }

    public List<Property> getPropertiesByPage(int page) {

        List<Property> allProperties = propertyRepository.findAllByOrderByCreatedAtAsc();
        List<Property> filteredProperties = new ArrayList<>();
        long maxProperties = propertyRepository.count();

        for (int i = (page * 9) - 9; i < page * 9; i++) {
            if (i < maxProperties) {
                filteredProperties.add(allProperties.get(i));
            } else {
                break;
            }
        }
        return filteredProperties;
    }

    public void removeAmenity(Property property, Amenity toDelete) {
        Property toRemoveFrom = propertyRepository.findById(property.getId()).orElse(null);
        toRemoveFrom.removeAmenity(toDelete);
        propertyRepository.save(toRemoveFrom);
    }
}
