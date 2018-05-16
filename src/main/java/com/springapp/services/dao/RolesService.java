package com.springapp.services.dao;

import com.springapp.entity.Role;
import com.springapp.repository.RoleRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.springapp.util.JSONResponse.*;

@Service
public class RolesService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        Iterable<Role> roles = roleRepository.findAll();
        List<Role> roleList = new ArrayList<Role>();

        for (Role r: roles) {
            roleList.add(r);
        }

        return roleList;
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findOne(roleId);
    }

    public JSONAware createRole(Role role) {
        roleRepository.save(role);
        return createOKResponse("Роль успешно создана");
    }

    public JSONAware updateRole(Role role) {
        roleRepository.save(role);
        return createOKResponse("Роль успешно обновлена");
    }

    public JSONAware deleteRole(Long roleId) {
        Role role = roleRepository.findOne(roleId);

        if (role == null) {
            return createNotFoundResponse("Роль не найдена по ID: " + roleId);
        }

        roleRepository.delete(role);
        return createOKResponse("Роль удалена: " + role.getRoleName());
    }

}
