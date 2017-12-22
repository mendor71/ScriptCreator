package com.springapp.repository;

import com.springapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mendor on 22.12.2017.
 */
@Repository(value = "appRepo")
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("FROM User WHERE userLogin = ?1")
//    List<User> findByUserLogin(String userLogin);
}
