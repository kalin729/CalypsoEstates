package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.service.AmenityService;
import com.kalinkrumov.calypso_estates.service.FilesStorageService;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class PropertyController {

    private final AmenityService amenityService;
    private final PropertyService propertyService;
    private final FilesStorageService filesStorageService;

    public PropertyController(AmenityService amenityService, PropertyService propertyService, FilesStorageService filesStorageService) {
        this.amenityService = amenityService;
        this.propertyService = propertyService;
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/properties/add")
    private String addProperty(Model model) {

        List<Amenity> amenities = amenityService.getAllAmenities();

        model.addAttribute("amenities", amenities);

        return "property-add";
    }

    @PostMapping("/properties/add")
    private String addProperty(@RequestParam("images") MultipartFile[] files,
                               @Valid PropertyAddDTO propertyAddDTO,
                               RedirectAttributes redirectAttributes) {

        if (files.length == 0) {
            redirectAttributes.addFlashAttribute("message", "Please select file/s to upload.");
            return "redirect:/";
        }

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            String newFileName = UUID.randomUUID() + fileExtension;
            if (filesStorageService.save(file, newFileName)) {
                images.add(new Image().setImageUrl(newFileName));
                propertyAddDTO.setMainImage(newFileName);
            }
        }
        propertyService.addProperty(propertyAddDTO, images);

        redirectAttributes.addFlashAttribute("message", "File upload successful.");

        return "redirect:/";
    }

    @GetMapping("/properties/{slug:.+}")
    private String propertyDetails(@PathVariable String slug, Model model) {
        Property property = propertyService.getPropertyBySlug(slug);

        model.addAttribute("property", property);

        return "property-single";
    }

    @GetMapping("/properties")
    public String properties(Model model) {

//        List<Property> properties = propertyService.getPropertiesByPage(page);

        List<Property> properties = propertyService.getAllProperties();

        model.addAttribute("properties", properties);

        return "property-grid";
    }

    @ModelAttribute
    public PropertyAddDTO propertyAddDTO() {
        return new PropertyAddDTO();
    }

}
