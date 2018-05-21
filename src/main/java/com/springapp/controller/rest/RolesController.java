package com.springapp.controller.rest;

import com.springapp.entity.Role;
import com.springapp.services.dao.RolesService;
import com.springapp.services.dao.UserRolesService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {
    private RolesService rolesService;
    private UserRolesService userRolesService;

    @Autowired
    public RolesController(RolesService rolesService, UserRolesService userRolesService) {
        this.rolesService = rolesService;
        this.userRolesService = userRolesService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Role> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Role getRoleById(@PathVariable Long roleId) {
        return rolesService.getRoleById(roleId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "{roleId}/users/{userId}", method = RequestMethod.POST)
    public JSONAware addUserRole(@PathVariable Long roleId, @PathVariable Long userId) {
        return userRolesService.addRole(userId, roleId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createRole(@RequestBody Role role) {
        return rolesService.createRole(role);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateRole(@RequestBody Role role) {
        return rolesService.updateRole(role);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "{roleId}/users/{userId}", method = RequestMethod.DELETE)
    public JSONAware removeUserRole(@PathVariable Long roleId, @PathVariable Long userId) {
        return userRolesService.removeRole(userId, roleId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public JSONAware deleteRole(@PathVariable Long roleId) {
        return rolesService.deleteRole(roleId);
    }
}
