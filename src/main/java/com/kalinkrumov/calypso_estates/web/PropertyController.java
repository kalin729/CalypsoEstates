package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PropertyController {

    private final String UPLOAD_DIR = "./src/main/resources/static/uploads/";
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties/add")
    private String addProperty(){
        return "property-add";
    }

    @PostMapping("/properties/add")
    private String addProperty(@RequestParam("images")MultipartFile[] files,
                               @Valid PropertyAddDTO propertyAddDTO,
                               RedirectAttributes redirectAttributes){
        if (files.length == 0) {
            redirectAttributes.addFlashAttribute("message", "Please select file/s to upload.");
            return "redirect:/";
        }

//        String[] fileNames = StringUtils.cleanPath(files.getOriginalFilename());
        try{
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                Path path = Paths.get(UPLOAD_DIR + fileName);
                Path path = Paths.get(UPLOAD_DIR + fileName).normalize();
                System.out.println("PATH:" + path.toString());
                System.out.println("PATH:" + path.toAbsolutePath());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                images.add(new Image().setImageUrl(fileName));
            }
//            propertyAddDTO.setImages(images);
            propertyService.addProperty(propertyAddDTO, images);
        }catch (IOException e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("message", "File upload successful.");

        return "redirect:/";

    }

}
