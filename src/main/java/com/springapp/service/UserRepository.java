package com.springapp.service;

import com.springapp.entity.Category;
import com.springapp.entity.Role;
import com.springapp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<User>();

    public UserRepository() {
        users.add(new User(1l, "mendor71", "Гущин", "Артем", "Андреевич", "$2a$10$le60IIomH87V4JWAVr6dPOPFPGyVucD5TqihQEVUTqRRFOKy7pki2"
                , new ArrayList<Category>(Arrays.asList(
                        new Category(1l, "Автомобили")
                        , new Category(2l, "Создание сайтов")))
                ,new ArrayList<Role>(Arrays.asList(new Role(1l,"ROLE_ADMIN"))) ));
        users.add(new User(2l, "miffan90", "Зольников", "Михаил", "Петрович", "$2a$10$HwOum/0ogxGBD6wNcuCJV.PRipFKPrCAVsHehVSf.ehSuDDrAnDnW"
                , new ArrayList<Category>(Arrays.asList(
                    new Category(3l, "Банковские продукту")
                    , new Category(4l, "Рекламные компании")))
                , new ArrayList<Role>(Arrays.asList(new Role(2l,"ROLE_USER")))));
    }

    public List<User> findAll() {
        return this.users;
    }

    public User findById(Long id) {
        for (User u: users) {
            if (u.getUserId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public User findByUserName(String login) {
        for (User u: users) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }
}
