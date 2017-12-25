package com.springapp.repository;

import com.springapp.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Mendor on 22.12.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByUserLogin(String userLogin);

    List<User> findByUserLastName(String lastName);

}
