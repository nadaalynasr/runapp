package com.example.runapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // show the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // temporary fake login just so the form works
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password) {
        // TODO: replace with real authentication later
        // for now just ignore creds and send them to home
        return "redirect:/";
    }
}
