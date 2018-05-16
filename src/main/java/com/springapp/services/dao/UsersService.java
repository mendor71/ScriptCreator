package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.controller.rest.UsersController;
import com.springapp.entity.Category;
import com.springapp.entity.Role;
import com.springapp.entity.State;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.StateRepository;
import com.springapp.repository.UserRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static com.springapp.util.JSONResponse.*;

@Service
public class UsersService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private AppProperties appProperties;
    @Autowired private StateRepository stateRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    public List<Category> getUserCategories(Long userId) {
        User user = userRepository.findOne(userId);
        return user.getUserCategoriesList();
    }


    public JSONAware createUser(User user) {
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
        userRepository.save(user);
        return createOKResponse("Пользователь успешно создан!");
    }


    public JSONAware updateUser(User user) {
        userRepository.save(user);
        return createOKResponse("Данные пользователя успешно обновлены!");
    }

    public JSONAware deleteUser(Long userId) {
        User user = userRepository.findOne(userId);

        if (user == null) {
            return createNotFoundResponse("Пользователь не найден по ID: " + userId);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        user.setUserStateId(state);
        userRepository.save(user);

        return createOKResponse("Пользователь отмечен как удаленный: " + user.getUserLogin());
    }

}
