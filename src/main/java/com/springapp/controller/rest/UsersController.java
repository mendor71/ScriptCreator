package com.springapp.controller.rest;

import com.springapp.entity.Category;
import com.springapp.entity.User;
import com.springapp.services.dao.UsersService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        Iterable<User> uList = usersService.getAllUsers();
        List<User> userList = new ArrayList<User>();
        for (User user: uList) {
            user.removeLinks();
            user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
            user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));
            userList.add(user);
        }
        return userList;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long userId) {
        User user = usersService.getUserById(userId);
        user.removeLinks();

        user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
        user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));

        return user;
    }

    @RequestMapping(value = "/{userId}/categories", method = RequestMethod.GET)
    public List<Category> getUserCategories(@PathVariable Long userId) {
        return usersService.getUserCategories(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createUser(@RequestBody User user) {
        return usersService.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateUser(@RequestBody User user) {
        return usersService.updateUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public JSONAware deleteUser(@PathVariable Long userId) {
        return usersService.deleteUser(userId);
    }
}
