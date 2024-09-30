package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.entity.User;
import com.example.edadil_microservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid
                                      @ModelAttribute
                                      User user, String confirmPassword, Model model) {

        log.info("starting registration process for user: {}", user.getUsername());

        if (!user.getPassword().equals(confirmPassword)) {
            log.info("Passwords do not match!");

            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username is already taken!");
            return "register";
        }
        try {
            userService.register(user.getUsername(), user.getPassword());
            //TODO сделать так , чтобы показывалось имя пользователя

            model.addAttribute("username", user.getUsername());
            return "/home";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {

        log.info("Showing login form");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin
            (@Valid
             @ModelAttribute User user, Model model) {

        log.info("Trying to login user with username: {}", user.getUsername());

        Optional<User> loggedInUser = userService.login(user.getUsername(), user.getPassword());


        if (loggedInUser.isPresent()) {
            //TODO

            log.info("User with username: {} successfully logged in", user.getUsername());

            model.addAttribute("username", loggedInUser.get().getUsername());


            return "redirect:/home";
        } else {

            System.out.println("ифэлс");
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}