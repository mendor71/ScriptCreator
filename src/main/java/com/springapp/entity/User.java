package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "user_last_name")
    private String userLastName;
    @Column(name = "user_first_name")
    private String userFirstName;
    @Column(name = "user_middle_name")
    private String userMiddleName;
    @Column(name = "user_password")
    private String userPassword;
    //private List<Category> userCategories;
    //private List<Role> userRoles;

    public User() {
    }

    public User(Integer userId, String userLogin, String userLastName, String userFirstName, String userMiddleName, String userPassword, ArrayList<Category> categories, ArrayList<Role> userRoles) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userLastName = userLastName;
        this.userFirstName = userFirstName;
        this.userMiddleName = userMiddleName;
        this.userPassword = userPassword;
        //this.userCategories = categories;
        //this.userRoles = userRoles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserMiddleName() {
        return userMiddleName;
    }

    public void setUserMiddleName(String userMiddleName) {
        this.userMiddleName = userMiddleName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

//    public List<Category> getUserCategories() {
//        return userCategories;
//    }
//
//    public void setUserCategories(List<Category> userCategories) {
//        this.userCategories = userCategories;
//    }


//    public List<Role> getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(List<Role> userRoles) {
//        this.userRoles = userRoles;
//    }
}
