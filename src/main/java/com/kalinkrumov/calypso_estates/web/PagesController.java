package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PagesController {

    private final PropertyService propertyService;

    public PagesController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping()
    private String index(Model model){
        return "index";
    }

    @GetMapping("/pages/all")
    private String all(Model model){

        List<Property> allProperties = propertyService.getAllProperties();

        model.addAttribute("allProperties", allProperties);

        return "all";
    }

    @GetMapping("/pages/moderators")
    private String moderators(){
        return "moderators";
    }

    @GetMapping("/pages/admins")
    public String admins(){
        return "admins";
    }

}
