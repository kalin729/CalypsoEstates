package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.AmenityAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.service.AmenityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping("/amenities/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes){

        if (!amenityService.deleteAmenity(id)) {
            redirectAttributes.addFlashAttribute("error", "Could not delete Amenity.");
        }
        return "redirect:/amenities/add";
    }

    @GetMapping("/amenities/add")
    public String addAmenity(Model model){

        List<Amenity> amenities = amenityService.getAllAmenities();

        model.addAttribute("amenities", amenities);

        return "amenity-add";
    }

    @PostMapping("/amenities/add")
    public String addAmenity(@Valid AmenityAddDTO amenityAddDTO, RedirectAttributes redirectAttributes){

        amenityService.addAmenity(amenityAddDTO);

        return "redirect:/amenities/add";
    }

}
