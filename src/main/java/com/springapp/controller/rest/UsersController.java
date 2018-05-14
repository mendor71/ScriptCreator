package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Role;
import com.springapp.entity.State;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.StateRepository;
import com.springapp.repository.UserRepository;
import com.springapp.services.dao.UsersService;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Integer userId) {
        return usersService.getUserById(userId);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}/categories", method = RequestMethod.GET)
    public List<Category> getUserCategories(@PathVariable Integer userId) {
        return usersService.getUserCategories(userId);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return usersService.createUser(user);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        return usersService.updateUser(user);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestParam(value = "userId") Integer userId) {
        return usersService.deleteUser(userId);
    }
}
