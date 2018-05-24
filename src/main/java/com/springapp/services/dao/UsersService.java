package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
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

import static com.springapp.util.JSONResponse.*;

@Service
public class UsersService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AppProperties appProperties;
    private StateRepository stateRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository, RoleRepository roleRepository, AppProperties appProperties, StateRepository stateRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.appProperties = appProperties;
        this.stateRepository = stateRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByUserLogin(login);
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


    public JSONAware updateUser(Long userId, User user) {
        User dbUser = userRepository.findOne(userId);

        if (user.getUserStateId() != null && user.getUserStateId().getStateId() != null
                && (dbUser.getUserStateId() == null || dbUser.getUserStateId().equals(user.getUserStateId())))
            dbUser.setUserStateId(stateRepository.findOne(user.getUserStateId().getStateId()));

        if (user.getUserPassword() != null && !bCryptPasswordEncoder.encode(user.getUserPassword()).equals(dbUser.getUserPassword()))
            dbUser.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));

        if (user.getUserFirstName() != null && !user.getUserFirstName().equals(dbUser.getUserFirstName()))
            dbUser.setUserFirstName(user.getUserFirstName());

        if (user.getUserLastName() != null && !user.getUserLastName().equals(dbUser.getUserLastName()))
            dbUser.setUserLastName(user.getUserLastName());

        if (user.getUserMiddleName() != null && !user.getUserMiddleName().equals(dbUser.getUserMiddleName()))
            dbUser.setUserMiddleName(user.getUserMiddleName());

        userRepository.save(dbUser);
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
