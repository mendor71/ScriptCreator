package com.springapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long catId;

    @Column(name = "cat_name")
    private String catName;

    @JoinColumn(name = "cat_state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State catStateId;

    @ManyToMany(mappedBy = "userCategoriesList")
    private List<User> categoryUsersList = new ArrayList<User>();

    public Category() {
    }

    public Category(Long catId, String catName, State catStateId) {
        this.catId = catId;
        this.catName = catName;
        this.catStateId = catStateId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Category category = (Category) o;

        return catId != null ? catId.equals(category.catId) : category.catId == null;
    }
}
