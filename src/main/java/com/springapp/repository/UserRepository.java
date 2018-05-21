package com.springapp.repository;

import com.springapp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Mendor on 22.12.2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserLogin(String userLogin);
}
