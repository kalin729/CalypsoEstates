package com.kalinkrumov.calypsoestates.web;

import com.kalinkrumov.calypsoestates.model.dto.UserRegisterDTO;
import com.kalinkrumov.calypsoestates.model.dto.UserRoleChangeDTO;
import com.kalinkrumov.calypsoestates.model.entity.User;
import com.kalinkrumov.calypsoestates.model.entity.UserRole;
import com.kalinkrumov.calypsoestates.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        return "redirect:login";
    }

    @GetMapping("/users/register")
    public String register() {
        return "register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !this.userService.registerUser(userRegisterDTO)) {
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
            return "redirect:register";
        }
        return "redirect:/";
    }

    @GetMapping("/users/all")
    public String allUsers(Model model) {
        List<User> users = userService.getAllUsers();
        List<UserRole> roles = userService.getAllUserRoles();

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "user-all";
    }

    @PostMapping("/users/edit_roles/{username}")
    public String editRoles(@PathVariable String username, @Valid UserRoleChangeDTO userRoleChangeDTO) {
        userService.changeUserRoles(username, userRoleChangeDTO);

        return "redirect:/users/all";
    }

    @PostMapping("/users/edit_active/{username}")
    public String editActive(@PathVariable String username) {
        userService.changeUserActiveStatus(username);

        return "redirect:/users/all";
    }

    @ModelAttribute
    public UserRoleChangeDTO userRoleChangeDTO() {
        return new UserRoleChangeDTO();
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

}
