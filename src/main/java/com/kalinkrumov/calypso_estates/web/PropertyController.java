package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.PropertyNotFoundException;
import com.kalinkrumov.calypso_estates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypso_estates.model.dto.PropertyAddDTO;
import com.kalinkrumov.calypso_estates.model.entity.Amenity;
import com.kalinkrumov.calypso_estates.model.entity.Image;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.service.AmenityService;
import com.kalinkrumov.calypso_estates.service.FilesStorageService;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
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
    public String addProperty(Model model) {

        List<Amenity> amenities = amenityService.getAllAmenities();

        model.addAttribute("amenities", amenities);

        return "property-add";
    }

    @PostMapping("/properties/add")
    public String addProperty(@RequestParam("images") List<MultipartFile> files,
                              @Valid PropertyAddDTO propertyAddDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("propertyAddDTO", propertyAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyAddDTO", bindingResult);
            return "redirect:/properties/add";
        }

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileExtension;
            try {
                fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            } catch (StringIndexOutOfBoundsException e) {
                redirectAttributes.addFlashAttribute("image_error", "Property must have at least one image.");
                redirectAttributes.addFlashAttribute("propertyAddDTO", propertyAddDTO);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.propertyAddDTO", bindingResult);
                return "redirect:/properties/add";
            }
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
    public String propertyDetails(@PathVariable String slug, Model model) {
        Property property = propertyService.getPropertyBySlug(slug);

        if (property == null) {
            throw new PropertyNotFoundException(slug);
        }

        model.addAttribute("property", property);

        return "property-single";
    }

    @GetMapping("/properties")
    public String properties(Model model, @PageableDefault(page = 0, size = 6) Pageable pageable) {

        model.addAttribute("properties", propertyService.getPropertiesByPage(pageable));

        return "property-grid";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({PropertyNotFoundException.class})
    public ModelAndView onPropertyNotFound(PropertyNotFoundException pnfe) {
        ModelAndView modelAndView = new ModelAndView("property-not-found");
        modelAndView.addObject("propertySlug", pnfe.getPropertySlug());

        return modelAndView;
    }

    @GetMapping("/properties/all")
    public String all(Model model) {

        List<Property> allProperties = propertyService.getAllProperties();

        model.addAttribute("allProperties", allProperties);

        return "property-all";
    }

    @GetMapping("/properties/edit/{slug}")
    public String edit(@PathVariable String slug, Model model) {
        PropertyAddDTO property = propertyService.getPropertyAddDTOBySlug(slug);
        List<Amenity> amenities = amenityService.getAllAmenities();

        model.addAttribute("toEdit", property);
        model.addAttribute("amenities", amenities);

        return "property-add";
    }

    @PostMapping("/properties/edit/{slug}")
    public String edit(@PathVariable String slug, @Valid PropertyAddDTO property, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult);
            redirectAttributes.addFlashAttribute("propertyAddDTO", property);
            return "property-add";
        }

        propertyService.updateProperty(slug, property);

        return "redirect:/properties/all";
    }

    @GetMapping("/properties/delete/{slug}")
    public String delete(@PathVariable String slug, RedirectAttributes redirectAttributes) {

        if (!propertyService.deleteProperty(slug)) {
            redirectAttributes.addFlashAttribute("error", "Could not delete property.");

            return "redirect:/properties/all";
        }

        return "redirect:/properties/all";
    }


    @ModelAttribute
    public PropertyAddDTO propertyAddDTO() {
        return new PropertyAddDTO();
    }

    @ModelAttribute
    public MessageSendDTO messageSendDTO() {
        return new MessageSendDTO();
    }

}
