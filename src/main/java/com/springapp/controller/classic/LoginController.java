package com.springapp.controller.classic;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Gushchin-AA1 on 08.09.2017.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String loginPage(ModelMap modelMap, @RequestParam(value = "error", required = false) boolean error) {
        if (error) {
            modelMap.addAttribute("errText", "Логин или пароль указаны некорректно!");
        }
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin")
    public String admin(ModelMap modelMap) {
        return "admin";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/user")
    public String user(ModelMap modelMap, HttpSession session) {
        return "user";
    }

    @RequestMapping(value = "/403")
    public String err403(ModelMap modelMap) {
        return "403";
    }
}
