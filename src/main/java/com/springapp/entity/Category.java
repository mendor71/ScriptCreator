package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

public class Category extends ResourceSupport {
    private Long catId;
    private String catName;

    public Category() {
    }

    public Category(Long catId, String catName) {
        this.catId = catId;
        this.catName = catName;
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
}
