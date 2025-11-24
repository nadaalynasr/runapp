package com.example.runapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StatsController {
    @GetMapping("/stats")
    public ModelAndView statsPage(Model model) {
        ModelAndView mv = new ModelAndView("stats");
        //centered title
        mv.addObject("pageTitle", "[Username]'S STATS");
        mv.addObject("totalRuns", "42");
        mv.addObject("totalDistance", "123.4 miles");
        mv.addObject("bestTime", "18:30");
        model.addAttribute("topRuns", java.util.List.of(
            new Run("Morning Sprint", "3.1 miles", "21:10", "2024-10-21"),
            new Run("Park Loop", "2.8 miles", "19:45", "2024-10-15"),
            new Run("Hill Climb", "4.0 miles", "32:20", "2024-10-02")
        ));
        return mv;
    }
    public static class Run {
        public String title;
        public String length;
        public String time;
        public String date;

        public Run(String title, String length, String time, String date) {
            this.title = title;
            this.length = length;
            this.time = time;
            this.date = date;
        }
    }
}

