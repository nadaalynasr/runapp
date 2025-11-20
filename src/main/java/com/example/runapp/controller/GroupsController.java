package com.example.runapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GroupsController {

    @GetMapping("/groups")
    public String groupsPage(Model model) {

        // Dummy group user IS in
        Map<String, Object> g1 = new HashMap<>();
        g1.put("id", 1);
        g1.put("name", "Morning Runners");
        g1.put("nextRunDisplay", "Tuesday 11/25/2025 07:00");
        g1.put("memberCount", 5);
        g1.put("isMember", true);

        // Dummy group user is NOT in
        Map<String, Object> g2 = new HashMap<>();
        g2.put("id", 2);
        g2.put("name", "Trail Squad");
        g2.put("nextRunDisplay", "Saturday 11/22/2025 08:00");
        g2.put("memberCount", 8);
        g2.put("isMember", false);

        model.addAttribute("myGroups", List.of(g1));
        model.addAttribute("discoverGroups", List.of(g2));

        return "groups";
    }
}
