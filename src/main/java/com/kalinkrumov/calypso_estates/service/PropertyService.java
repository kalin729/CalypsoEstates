package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.model.entity.Status;
import com.kalinkrumov.calypso_estates.model.enums.StatusEnum;
import com.kalinkrumov.calypso_estates.repository.PropertyRepository;
import com.kalinkrumov.calypso_estates.repository.StatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final StatusRepository statusRepository;
    private final FilesStorageService filesStorageService;
    private final ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, StatusRepository statusRepository, FilesStorageService filesStorageService, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.statusRepository = statusRepository;
        this.filesStorageService = filesStorageService;
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
        String slug = propertyAddDTO.getTitle().replaceAll("\s+", "-").toLowerCase();
        property.setSlug(slug);
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

    public Page<Property> getPropertiesByPage(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    public void removeAmenity(Amenity toDelete) {
        for (Property p : toDelete.getProperties()) {
            p.removeAmenity(toDelete);
            propertyRepository.save(p);
        }

    }

    public PropertyAddDTO getPropertyAddDTOBySlug(String slug) {
        return modelMapper.map(propertyRepository.findBySlug(slug), PropertyAddDTO.class);
    }

    public void updateProperty(String slug, PropertyAddDTO propertyAddDTO) {
        Property property = modelMapper.map(propertyAddDTO, Property.class);
        property.setId(propertyRepository.findBySlug(slug).getId());
        property.setStatus(statusRepository.findByStatus(propertyAddDTO.getStatus()));
        property.setImages(propertyRepository.findBySlug(slug).getImages());
        property.setMainImage(propertyRepository.findBySlug(slug).getMainImage());
        property.setCreatedAt(propertyRepository.findBySlug(slug).getCreatedAt());

        propertyRepository.save(property);
    }

    public boolean deleteProperty(String slug) {
        Property toDelete = propertyRepository.findBySlug(slug);

        toDelete.getAmenities().removeAll(toDelete.getAmenities());
        toDelete.getImages().forEach(i -> filesStorageService.delete(i.getImageUrl()));
        toDelete.getImages().removeAll(toDelete.getImages());

        propertyRepository.save(toDelete);

        propertyRepository.delete(toDelete);

        return true;
    }
}
