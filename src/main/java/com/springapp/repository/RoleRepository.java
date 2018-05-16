package com.springapp.repository;

import com.springapp.entity.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
