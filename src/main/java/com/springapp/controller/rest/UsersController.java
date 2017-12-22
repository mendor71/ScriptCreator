package com.springapp.controller.rest;

import com.springapp.entity.Category;
import com.springapp.entity.User;
import com.springapp.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user: users) {
            user.removeLinks();

            user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
            user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));
        }
        return users;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId);

        user.removeLinks();

        user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
        user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));
        return user;
    }

    @RequestMapping(value = "/{userId}/categories", method = RequestMethod.GET)
    public List<Category> getUserCategories(@PathVariable Long userId) {
        User user = userRepository.findById(userId);
        List<Category> categories = user.getUserCategories();
        return categories;
    }


}
