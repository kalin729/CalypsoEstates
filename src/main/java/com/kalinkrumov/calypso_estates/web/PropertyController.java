package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.service.FilesStorageService;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PropertyController {

//    private final String UPLOAD_DIR = "./src/main/uploads/";
    private final PropertyService propertyService;
    private final FilesStorageService filesStorageService;

    public PropertyController(PropertyService propertyService, FilesStorageService filesStorageService) {
        this.propertyService = propertyService;
        this.filesStorageService = filesStorageService;
    }

    @GetMapping("/properties/add")
    private String addProperty() {
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
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                Path path = Paths.get(UPLOAD_DIR + fileName).normalize();
//                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            filesStorageService.save(file);
            images.add(new Image().setImageUrl(fileName));
        }
        propertyService.addProperty(propertyAddDTO, images);

        redirectAttributes.addFlashAttribute("message", "File upload successful.");

        return "redirect:/";
    }

    @GetMapping("/properties/{slug:.+}")
    private String propertyDetails(@PathVariable String slug, Model model){
        Property property = propertyService.getPropertyBySlug(slug);

        model.addAttribute("property", property);

        return "property-details.html";
    }

}
