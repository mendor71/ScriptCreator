package com.springapp.controller.rest;

import com.springapp.entity.Role;
import com.springapp.entity.User;
import com.springapp.repository.RoleRepository;
import com.springapp.repository.UserRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRoles")
public class UserRolesController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity add(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roleId") Integer roleId) {

        User user = userRepository.findOne(userId);
        Role role = roleRepository.findOne(roleId);

        if (user == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "User not found by ID: " + userId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (role == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Role not found by ID: " + roleId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!user.getUserRolesList().contains(role)) {
            user.getUserRolesList().add(role);
            userRepository.save(user);
            roleRepository.save(role);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "role: " + role.getRoleName() + " added to user " + user.getUserLogin()), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity remove(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "roleId") Integer roleId) {

        User user = userRepository.findOne(userId);
        Role role = roleRepository.findOne(roleId);

        if (user == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "User not found by ID: " + userId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (role == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Role not found by ID: " + roleId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getUserRolesList().contains(role)) {
            user.getUserRolesList().remove(role);
            userRepository.save(user);
            roleRepository.save(role);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "role: " + role.getRoleName() + " removed from user " + user.getUserLogin()), HttpStatus.OK);
    }

}
