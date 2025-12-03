package com.example.runapp.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.runapp.models.InputMetrics;
import com.example.runapp.models.User;
import com.example.runapp.services.CreateGroupService;
import com.example.runapp.services.UserService;

@Controller
public class CreateGroupController {
    
    private final CreateGroupService createGroupService;
    private final UserService userService;

    @Autowired
    public CreateGroupController(CreateGroupService createGroupService, UserService userService) {
        this.createGroupService = createGroupService;
        this.userService = userService;
    }

    @GetMapping("/create-group")
    public ModelAndView showCreateCroupPage() {
        ModelAndView mv = new ModelAndView("create_group");
        //centered title
        mv.addObject("pageTitle", "NEW GROUP RUN");
        //input title
        mv.addObject("id", "create-group-name");
        mv.addObject("name", "createGroupName");
        mv.addObject("placeholder", "Enter group name...");
        //input metrics
        List<InputMetrics> metrics = List.of(
            new InputMetrics("date-id", "date", "SCHEDULE DATE", "date"),
            new InputMetrics("time-id", "time", "SCHEDULE TIME", "time"),
            new InputMetrics("max-id", "memberMax", "MEMBER MAX", "number")
        );
         mv.addObject("metrics", metrics);
         mv.addObject("buttonText", "POST GROUP RUN");

        return mv;
    }

    @PostMapping("/create-group")
    public String createGroup(@RequestParam String createGroupName, 
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam int memberMax)
            throws SQLException {

        // String userId = userService.getLoggedInUser().getUserId();
        String userId = "1"; // temp user id

        createGroupService.createGroup(userId, createGroupName, date, time, memberMax);
        
        return "redirect:/groups";
    }
}
