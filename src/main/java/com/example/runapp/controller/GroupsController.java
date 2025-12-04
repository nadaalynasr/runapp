package com.example.runapp.controller;

import com.example.runapp.models.GroupsView;
import com.example.runapp.services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @GetMapping("/groups")
    public String groupsPage(Model model) {

        int userId = 1; // TODO: pull from session when login is working

        List<GroupsView> myGroups = groupsService.getGroupsForUser(userId);
        List<GroupsView> discoverGroups = groupsService.getGroupsUserIsNotIn(userId);

        model.addAttribute("myGroups", myGroups);
        model.addAttribute("discoverGroups", discoverGroups);

        return "groups";
    }

    @PostMapping("/groups/{groupId}/join")
    public String joinGroup(@PathVariable int groupId) {
        int userId = 1;
        groupsService.joinGroup(userId, groupId);
        return "redirect:/groups";
    }

    @PostMapping("/groups/{groupId}/leave")
    public String leaveGroup(@PathVariable int groupId) {
        int userId = 1;
        groupsService.leaveGroup(userId, groupId);
        return "redirect:/groups";
    }
}
