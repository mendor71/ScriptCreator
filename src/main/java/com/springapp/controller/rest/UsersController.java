package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Role;
import com.springapp.entity.State;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.StateRepository;
import com.springapp.repository.UserRepository;
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
    private AppProperties appProperties;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> userList = new ArrayList<User>();
        for (User user: users) {
            user.removeLinks();
            user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
            user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));
            userList.add(user);
        }
        return userList;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Integer userId) {
        User user = userRepository.findOne(userId);
        user.removeLinks();

        user.add(linkTo(UsersController.class).slash(user.getUserId()).withSelfRel());
        user.add(linkTo(methodOn(UsersController.class).getUserCategories(user.getUserId())).withRel("allCategories"));
        return user;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}/categories", method = RequestMethod.GET)
    public List<Category> getUserCategories(@PathVariable Integer userId) {
        User user = userRepository.findOne(userId);
        return user.getUserCategoriesList();
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        if (user.getUserRolesList() == null || user.getUserRolesList().size() == 0) {
            Role role = roleRepository.findByRoleName(appProperties.getUserDefaultRole());
            user.setUserRolesList(new ArrayList<Role>(Collections.singletonList(role)));
        }
        if (user.getUserStateId() == null) {
            State state = stateRepository.findByStateName(appProperties.getUserDefaultState());
            user.setUserStateId(state);
        }

        String password = bCryptPasswordEncoder.encode(user.getUserPassword());

        user.setUserPassword(password);
        user = userRepository.save(user);
        return user;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestParam(value = "userId") Integer userId) {
        User user = userRepository.findOne(userId);

        if (user == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "User not found by ID: " + userId), HttpStatus.NOT_ACCEPTABLE);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        user.setUserStateId(state);
        userRepository.save(user);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "User mark as deleted: " + user.getUserLogin()), HttpStatus.OK);
    }
}
