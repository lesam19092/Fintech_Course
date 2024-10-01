
package com.example.edadil_microservice.controller;

import com.example.edadil_microservice.entity.User;
import com.example.edadil_microservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final UserService userService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "home";
    }
}
