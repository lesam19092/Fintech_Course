package org.example.foodru_microservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {


    @GetMapping("check")
    public String check(Principal principal) {
        return principal.getName();
    }

}
