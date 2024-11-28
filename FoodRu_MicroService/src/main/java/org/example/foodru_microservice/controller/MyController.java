package org.example.foodru_microservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MyController {


    @GetMapping("check")
    public void check(Principal principal) {
        System.out.println(principal.getClass());

    }

}
