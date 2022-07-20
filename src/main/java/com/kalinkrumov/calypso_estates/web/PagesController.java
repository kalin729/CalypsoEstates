package com.kalinkrumov.calypso_estates.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping()
    private String index(Model model){
        return "index";
    }

    @GetMapping("/pages/all")
    private String all(){
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
