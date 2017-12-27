package com.springapp.controller.rest;

import com.springapp.entity.Role;
import com.springapp.repository.RoleRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RolesController {

    @Autowired
    private RoleRepository roleRepository;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Role> getAllRoles() {
        Iterable<Role> roles = roleRepository.findAll();
        List<Role> roleList = new ArrayList<Role>();

        for (Role r: roles) {
            roleList.add(r);
        }

        return roleList;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Role getRoleById(Integer roleId) {
        Role role = roleRepository.findOne(roleId);
        return role;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Role createRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Role updateRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(@RequestParam(value = "roleId") Integer roleId) {
        Role role = roleRepository.findOne(roleId);

        if (role == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Role not found by ID: " + roleId), HttpStatus.NOT_ACCEPTABLE);
        }

        roleRepository.delete(role);
        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Role deleted: " + role.getRoleName()), HttpStatus.OK);
    }
}
