package com.example.runapp.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.example.runapp.services.UserService;
import com.example.runapp.services.RunService;
import com.example.runapp.models.Run;

/**
 * This controller handles the home page and some of it's sub URLs.
 */
@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RunService runService;


    /**
     * This is the specific function that handles the root URL itself.
     *
     * Note that this accepts a URL parameter called error.
     * The value to this parameter can be shown to the user as an error message.
     * See notes in HashtagSearchController.java regarding URL parameters.
     */
    @GetMapping
    public ModelAndView webpage(@RequestParam(name = "error", required = false) String error) {
        // See notes on ModelAndView in BookmarksController.java.
        ModelAndView mv = new ModelAndView("home");
        List<Run> runs = null;
        try{
            if (userService.isAuthenticated()) {
                String userId = userService.getLoggedInUser().getUserId();
                runs = runService.getRuns();
            } else {
                // runs = Utility.createSamplerunsListWithoutComments(); from old proj 2 code
            }
            mv.addObject("runs", runs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If an error occured, you can set the following property with the
        // error message to show the error message to the user.
        // An error message can be optionally specified with a url query parameter too.
        String errorMessage = error;
        mv.addObject("errorMessage", errorMessage);
        return mv;
    }

}
