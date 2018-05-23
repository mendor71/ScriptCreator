package com.springapp.controller.classic;

import com.springapp.services.RestMappingApiDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Gushchin-AA1 on 08.09.2017.
 */
@Controller
@RequestMapping("/")
public class MainController {
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap modelMap) {
        return "index";
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public String api(ModelMap modelMap) {
        return "api";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/menu/categories")
    public String categoriesPage(ModelMap modelMap) {
        return "categories";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET, value = "/menu/scenarios")
    public String requestResponsePage(ModelMap modelMap) {
        return "scenarios";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "testUsers", method = RequestMethod.GET)
    public String testUsers(ModelMap modelMap) {
        return "test";
    }
}
