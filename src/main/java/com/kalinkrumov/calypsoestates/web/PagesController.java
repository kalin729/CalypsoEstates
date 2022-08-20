package com.kalinkrumov.calypsoestates.web;

import com.kalinkrumov.calypsoestates.model.entity.Property;
import com.kalinkrumov.calypsoestates.service.PropertyService;
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
    public String index(Model model){

        List<Property> threeRandomProperties = propertyService.getThreeRandomProperties();

        model.addAttribute("threeRandomProperties", threeRandomProperties);

        return "index";
    }

    @GetMapping("/pages/admins")
    public String admins(){
        return "redirect:/messages/all";
    }

    @GetMapping("/pages/moderators")
    public String moderators(){
        return "moderators";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts(){
        return "contact";
    }

}
