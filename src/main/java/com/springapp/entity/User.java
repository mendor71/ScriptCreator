package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "public.users")
public class User extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

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

    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "ur_user_id")}, inverseJoinColumns = {@JoinColumn(name = "ur_role_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> userRolesList = new ArrayList<Role>();

    @JoinColumn(name = "user_state_id", referencedColumnName = "state_id")
    @ManyToOne(optional = false)
    private State userStateId;

    public User() {
    }

    public User(Long userId, String userLogin, String userLastName, String userFirstName, String userMiddleName, String userPassword, ArrayList<Category> categories, ArrayList<Role> userRoles) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.userLastName = userLastName;
        this.userFirstName = userFirstName;
        this.userMiddleName = userMiddleName;
        this.userPassword = userPassword;
        //this.userCategories = categories;
        //this.userRoles = userRoles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public State getUserStateId() {
        return userStateId;
    }

    public void setUserStateId(State userStateId) {
        this.userStateId = userStateId;
    }

    public List<Role> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<Role> userRolesList) {
        this.userRolesList = userRolesList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userMiddleName='" + userMiddleName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRolesList=" + userRolesList +
                ", userStateId=" + userStateId +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        return userId != null ? userId.equals(user.userId) : user.userId == null;
    }
}
