package com.springapp;

import com.springapp.entity.User;
import com.springapp.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
public class RepoTest {
    @Autowired
    private UserRepository userRepo;

    @Test
    public void testFindOne() {
       List<User> user = userRepo.findByUserLogin("tester_1");
       System.out.println(user);
    }

    @Test
    public void testFindAll() {
        Iterable<User> user = userRepo.findAll();
        System.out.println(user);
    }
}
