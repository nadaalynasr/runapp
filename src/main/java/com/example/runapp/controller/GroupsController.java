package com.example.runapp.controller;

import com.example.runapp.models.GroupsView;
import com.example.runapp.services.GroupsService;
import com.example.runapp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupsController {

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private UserService userService;

    @GetMapping("/groups")
    public String groupsPage(Model model) {

        if (userService == null || !userService.isAuthenticated()) {
            return "redirect:/login";
        }

        int userId = Integer.parseInt(userService.getLoggedInUser().getUserId());

        try {
            List<GroupsView> myGroups = groupsService.getGroupsForUser(userId);
            List<GroupsView> discoverGroups = groupsService.getGroupsUserIsNotIn(userId);

            model.addAttribute("myGroups", myGroups);
            model.addAttribute("discoverGroups", discoverGroups);

            return "groups";
        } catch (RuntimeException e) {
            e.printStackTrace();
            // If there's a backend error (DB, etc.), redirect to home instead of showing Whitelabel error
            return "redirect:/";
        }
    }

    @PostMapping("/groups/{groupId}/join")
    public String joinGroup(@PathVariable int groupId) {
        if (userService == null || !userService.isAuthenticated()) {
            return "redirect:/login";
        }
        int userId = Integer.parseInt(userService.getLoggedInUser().getUserId());
        try {
            groupsService.joinGroup(userId, groupId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/groups";
    }

    @PostMapping("/groups/{groupId}/leave")
    public String leaveGroup(@PathVariable int groupId) {
        if (userService == null || !userService.isAuthenticated()) {
            return "redirect:/login";
        }
        int userId = Integer.parseInt(userService.getLoggedInUser().getUserId());
        try {
            groupsService.leaveGroup(userId, groupId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/groups";
    }
    
}
