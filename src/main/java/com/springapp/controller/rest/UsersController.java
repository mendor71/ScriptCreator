package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Role;
import com.springapp.entity.State;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.StateRepository;
import com.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
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

}
