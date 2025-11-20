package com.example.runapp.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.runapp.services.LogRunService;
import com.example.runapp.models.InputMetrics;

@Controller
public class LogRunController {

    @Autowired
    private LogRunService logRunService;

    @GetMapping("/log-run")
    public ModelAndView showLogRunPage() {
        String inputPlaceholder = logRunService.getPlaceholderForTime(LocalTime.now());
        ModelAndView mv = new ModelAndView("log_run");
        //centered title
        mv.addObject("pageTitle", "LOG A RUN");
        //input title
        mv.addObject("id", "run-title");
        mv.addObject("name", "run_title");
        mv.addObject("placeholder", inputPlaceholder);
        //input metrics
        List<InputMetrics> metrics = List.of(
            new InputMetrics("time-id", "time", "ENTER TIME", "text"),
            new InputMetrics("distance-id", "distance", "ENTER DISTANCE", "number"),
            new InputMetrics("hr-id", "avg_hr", "ENTER BPM", "number")
        );
         mv.addObject("metrics", metrics);
         mv.addObject("buttonText", "SAVE RUN");

        return mv;
    }

    @PostMapping("/log-run")
    public String logRun(@RequestParam String distance, @RequestParam String duration) {
        // Process the run log data here
        return "redirect:/home";
    }
}