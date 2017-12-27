package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "public.categories")
public class Category extends ResourceSupport {
    @Id
    @Column(name = "cat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catId;
    @Column(name = "cat_name")
    private String catName;
    @JoinColumn(name = "cat_state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State catStateId;
    @ManyToMany(mappedBy = "userCategoriesList")
    private List<User> categoryUsersList = new ArrayList<User>();
    @OneToMany(mappedBy = "respCategory")
    private List<Response> responseList = new ArrayList<Response>();
    @OneToMany(mappedBy = "reqCategory")
    private List<Request> requests = new ArrayList<Request>();

    public Category() {
    }

    public Category(Integer catId, String catName, State catStateId) {
        this.catId = catId;
        this.catName = catName;
        this.catStateId = catStateId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public State getCatStateId() {
        return catStateId;
    }

    public void setCatStateId(State catStateId) {
        this.catStateId = catStateId;
    }

    public List<User> getCategoryUsersList() {
        return categoryUsersList;
    }

    public void setCategoryUsersList(List<User> categoryUsersList) {
        this.categoryUsersList = categoryUsersList;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
