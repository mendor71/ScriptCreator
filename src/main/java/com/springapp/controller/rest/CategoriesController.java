package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.StateRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StateRepository stateRepository;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Category> findAll() {
        Iterable<Category> categories = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<Category>();
        for (Category c: categories) {
            categoryList.add(c);
        }
        return categoryList;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{catId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable Integer catId) {
        Category category = categoryRepository.findOne(catId);

        category.removeLinks();
        category.add(linkTo(CategoriesController.class).slash(category.getCatId()).withSelfRel());

        return category;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Category createCategory(@RequestBody Category category) {
        if (category.getCatStateId() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            category.setCatStateId(state);
        }

        category = categoryRepository.save(category);
        return category;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Category updateCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteCategory(@RequestParam(value = "catId") Integer catId) {
        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());

        Category category = categoryRepository.findOne(catId);

        if (category == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Category not found by ID: " + catId), HttpStatus.NOT_ACCEPTABLE);
        }

        category.setCatStateId(state);
        categoryRepository.save(category);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Category mark as deleted: " + catId), HttpStatus.OK);
    }
}
