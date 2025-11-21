package com.example.runapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RegisterController {
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.mustache
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String email,
                                 @RequestParam String username,
                                 @RequestParam String password) {
        // TODO: add real registration logic later (insert into users table, hash password, etc.)

        // For now, just send them to login
        return "redirect:/login";
    }
}
