package com.example.runapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.runapp.models.TrendingView;
import com.example.runapp.services.GroupsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TrendingController {

    @Autowired
    private GroupsService groupsService;
    @GetMapping("/trending")
    public String trendingPage(Model model) {

        // placeholder empty list — angel will implement logic
        List<TrendingView> trendingGroups = groupsService.getTrendingGroups();
        model.addAttribute("trendingGroups", trendingGroups);

        return "trending_groups";
    }
}


