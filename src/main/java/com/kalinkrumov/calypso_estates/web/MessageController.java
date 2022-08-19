package com.kalinkrumov.calypso_estates.web;

import com.kalinkrumov.calypso_estates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypso_estates.model.entity.Message;
import com.kalinkrumov.calypso_estates.model.entity.Property;
import com.kalinkrumov.calypso_estates.service.MessageService;
import com.kalinkrumov.calypso_estates.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
public class MessageController {

    private final MessageService messageService;
    private final PropertyService propertyService;

    public MessageController(MessageService messageService, PropertyService propertyService) {
        this.messageService = messageService;
        this.propertyService = propertyService;
    }

    @PostMapping("/message/send")
    public String sendMessage(@Valid MessageSendDTO messageSendDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageSendDTO", messageSendDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:/contacts";
        }
        messageService.addMessage(messageSendDTO);
        return "redirect:/contacts";
    }

    @GetMapping("/message/all")
    public String allMessages(Model model) {

        List<Message> messages = messageService.getAllMessages();

        model.addAttribute("messages", messages);

        return "message-all";
    }

    @GetMapping("/message/view/{id}")
    public String allMessages(@PathVariable("id") UUID id, Model model) {

        Message message = messageService.getMessageById(id);

        if (message.getProperty() != null) {
            Property property = propertyService.getPropertyBySlug(message.getProperty());
            model.addAttribute("property", property);
        }

        model.addAttribute("message", message);

        return "message-view";
    }

    @PostMapping("/message/reply/{id}")
    public String sendMessage(@PathVariable("id") UUID id, @RequestParam("reply") String reply, RedirectAttributes redirectAttributes) {

        messageService.addReply(id, reply);

        return "redirect:/message/view/" + id;
    }

    @ModelAttribute
    public MessageSendDTO messageSendDTO() {
        return new MessageSendDTO();
    }

}
