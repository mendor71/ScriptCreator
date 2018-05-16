package com.springapp.services.dao;

import com.springapp.entity.Role;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.UserRepository;
import com.springapp.util.JSONResponse;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class UserRolesService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;

    public JSONAware addRole(Long userId, Long roleId) {

        User user = userRepository.findOne(userId);
        Role role = roleRepository.findOne(roleId);

        if (user == null) { return createNotFoundResponse("Пользователь не найден по ID: " + userId); }
        if (role == null) { return createNotFoundResponse("Роль не найдена по ID: " + roleId); }

        if (!user.getUserRolesList().contains(role)) {
            user.getUserRolesList().add(role);
            userRepository.save(user);
            roleRepository.save(role);
        }

        return createOKResponse("Роль: " + role.getRoleName() + " успешно добавлена пользователю: " + user.getUserLogin());
    }

    public JSONAware removeRole(Long userId, Long roleId) {

        User user = userRepository.findOne(userId);
        Role role = roleRepository.findOne(roleId);

        if (user == null) { return createNotFoundResponse("Пользователь не найден по ID: " + userId); }
        if (role == null) { return createNotFoundResponse("Роль не найдена по ID: " + roleId); }


        if (user.getUserRolesList().contains(role)) {
            user.getUserRolesList().remove(role);
            userRepository.save(user);
            roleRepository.save(role);
        }

        return createOKResponse("Роль: " + role.getRoleName() + " успешно удалена у пользователя: " + user.getUserLogin());
    }
}
