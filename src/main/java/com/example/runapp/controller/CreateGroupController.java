package com.example.runapp.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.runapp.models.InputMetrics;

@Controller
public class CreateGroupController {
    @GetMapping("/create-group")
    public ModelAndView showCreateCroupPage() {
        ModelAndView mv = new ModelAndView("create_group");
        //centered title
        mv.addObject("pageTitle", "NEW GROUP");
        //input title
        mv.addObject("id", "create-group-title");
        mv.addObject("name", "create_group_title");
        mv.addObject("placeholder", "Enter group name...");
        //input metrics
        List<InputMetrics> metrics = List.of(
            new InputMetrics("day-id", "day", "ENTER DAY", "text"),
            new InputMetrics("time-id", "time", "ENTER TIME", "text"),
            new InputMetrics("max-id", "member_max", "MEMBER MAX", "number")
        );
         mv.addObject("metrics", metrics);
         mv.addObject("buttonText", "POST GROUP");

        return mv;
    }

    @PostMapping("/create-group")
    public String createGroup(@RequestParam String distance, @RequestParam String duration) {
        return "redirect:/home";
    }
}
