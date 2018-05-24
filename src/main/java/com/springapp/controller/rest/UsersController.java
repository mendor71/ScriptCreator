package com.springapp.controller.rest;

import com.springapp.IncludeAPI;
import com.springapp.entity.User;
import com.springapp.services.dao.UsersService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        Iterable<User> uList = usersService.findAllUsers();
        List<User> userList = new ArrayList<User>();
        for (User user: uList) {
            user.removeLinks();
            user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
            userList.add(user);
        }
        return userList;
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long userId) {
        User user = usersService.findUserById(userId);
        user.removeLinks();

        user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
        return user;
    }

    @IncludeAPI(arguments = "Users user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createUser(@RequestBody User user) {
        return usersService.createUser(user);
    }

    @IncludeAPI(arguments = "Users user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public JSONAware updateUser(@PathVariable Long userId, @RequestBody User user) {
        return usersService.updateUser(userId, user);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public JSONAware deleteUser(@PathVariable Long userId) {
        return usersService.deleteUser(userId);
    }
}
