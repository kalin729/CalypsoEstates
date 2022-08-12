package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.AmenityAddDTO;
import com.kalinkrumov.calypso_estates.service.AmenityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping("/amenities/add")
    public String addAmenity(){
        return "amenity-add";
    }

    @PostMapping("/amenities/add")
    public String addAmenity(@Valid AmenityAddDTO amenityAddDTO, RedirectAttributes redirectAttributes){

        amenityService.addAmenity(amenityAddDTO);

        return "amenity-add";
    }

}
