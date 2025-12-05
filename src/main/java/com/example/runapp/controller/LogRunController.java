package com.example.runapp.controller;

import java.time.LocalTime;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.runapp.services.LogRunService;
import com.example.runapp.services.RunService;
import com.example.runapp.services.UserService;
import com.example.runapp.models.InputMetrics;

@Controller
public class LogRunController {

    @Autowired
    private LogRunService logRunService;

    @Autowired
    private RunService runService;

    @Autowired
    private UserService userService;

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
            new InputMetrics("time-id", "time", "ENTER TIME", "text", "hh:mm:ss"),
            new InputMetrics("distance-id", "distance", "ENTER DISTANCE", "number", "meters"),
            new InputMetrics("hr-id", "bpm", "ENTER BPM", "number", "bpm")
        );
         mv.addObject("metrics", metrics);
         mv.addObject("buttonText", "SAVE RUN");

        return mv;
    }

    @PostMapping("/log-run")
    public String logRun(
        @RequestParam(name="run_title", required=false) String title,
        @RequestParam(name="time", required=false) String timeStr,
        @RequestParam(name="distance", required=false) String distanceStr,
        @RequestParam(name="bpm", required=false) Integer bpmParam,
        @RequestParam(name="share", required=false) String share) {
        boolean shareToFeed = (share != null);

        double distanceMeters = 0d;
        if (distanceStr != null && !distanceStr.isBlank()) {
            try {
                distanceMeters = Double.parseDouble(distanceStr);
            } catch (NumberFormatException nfe) {
                distanceMeters = 0d;
            }
        }

        int elapsedSeconds = 0;
        if (timeStr != null && !timeStr.isBlank()) {
            try {
                String[] parts = timeStr.split(":");
                if (parts.length == 3) {
                    int hh = Integer.parseInt(parts[0]);
                    int mm = Integer.parseInt(parts[1]);
                    int ss = Integer.parseInt(parts[2]);
                    elapsedSeconds = hh * 3600 + mm * 60 + ss;
                } else if (parts.length == 2) {
                    int mm = Integer.parseInt(parts[0]);
                    int ss = Integer.parseInt(parts[1]);
                    elapsedSeconds = mm * 60 + ss;
                } else {
                    elapsedSeconds = Integer.parseInt(parts[0]);
                }
            } catch (Exception e) {
                elapsedSeconds = 0;
            }
        }

        double bpm = 0d;
        if (bpmParam != null) {
            bpm = bpmParam.doubleValue();
        }

        //local time = run's end time
        //start time = end time - elapsed time
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        Timestamp startTime = new Timestamp(endTime.getTime() - (elapsedSeconds * 1000L));
        Date runDate = new Date(endTime.getTime());

        Integer userId = 1;
        try {
            if (userService != null && userService.isAuthenticated()) {
                String uid = userService.getLoggedInUser().getUserId();
                if (uid != null) {
                    userId = Integer.parseInt(uid);
                }
            }
        } catch (Exception e) {
            userId = 1;
        }

        try {
            boolean created = runService.createRun(userId, runDate, startTime, endTime, elapsedSeconds, distanceMeters, bpm);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}