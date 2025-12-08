package com.example.runapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.example.runapp.services.UserService;
import com.example.runapp.services.StatsService;
import com.example.runapp.services.StatsService.StatsDTO;
import com.example.runapp.services.StatsService.RunCard;
import com.example.runapp.models.User;

import java.util.List;

@Controller
public class StatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
public ModelAndView statsPage(Model model) {
    ModelAndView mv = new ModelAndView("stats");

    User user = userService.getLoggedInUser();
    
    //userId is a STRING now
    String userId = user.getUserId(); // <-- This is the correct getter

    // recompute stats + update stats table
    StatsDTO stats = statsService.updateAndGetStatsForUser(userId);

    mv.addObject("pageTitle", user.getFirstName() + "'s STATS");
    mv.addObject("totalRuns", stats.totalRuns);

    double miles = stats.totalDistanceMeters / 1609.34;
    mv.addObject("totalDistance", String.format("%.1f miles", miles));

    mv.addObject("bestTime",
        stats.bestTimeSeconds == null ? "—" : formatSeconds(stats.bestTimeSeconds));

    // top runs for the cards
    List<RunCard> topRuns = statsService.getTopRunsForUser(userId);
    model.addAttribute("topRuns", topRuns);

    return mv;
}

    private String formatSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}

