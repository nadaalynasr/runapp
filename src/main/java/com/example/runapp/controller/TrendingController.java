package com.example.runapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TrendingController {

    @GetMapping("/trending")
    public String trendingPage(Model model) {

        // placeholder empty list — angel will implement logic
        model.addAttribute("trendingGroups", List.of());

        return "trending_groups";
    }
}


