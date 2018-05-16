package com.springapp.controller.rest;

import com.springapp.entity.Role;
import com.springapp.repository.RoleRepository;
import com.springapp.services.dao.RolesService;
import com.springapp.services.dao.UserRolesService;
import com.springapp.util.JSONResponse;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {

    @Autowired private RolesService rolesService;
    @Autowired private UserRolesService userRolesService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Role> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Role getRoleById(@PathVariable Long roleId) {
        return rolesService.getRoleById(roleId);
    }

    @RequestMapping(value = "{roleId}/users/{userId}", method = RequestMethod.POST)
    public JSONAware addUserRole(@PathVariable Long roleId, @PathVariable Long userId) {
        return userRolesService.addRole(userId, roleId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createRole(@RequestBody Role role) {
        return rolesService.createRole(role);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateRole(@RequestBody Role role) {
        return rolesService.updateRole(role);
    }

    @RequestMapping(value = "{roleId}/users/{userId}", method = RequestMethod.DELETE)
    public JSONAware removeUserRole(@PathVariable Long roleId, @PathVariable Long userId) {
        return userRolesService.removeRole(userId, roleId);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public JSONAware deleteRole(@PathVariable Long roleId) {
        return rolesService.deleteRole(roleId);
    }
}
