package com.springapp.controller.rest;

import com.springapp.entity.Category;
import com.springapp.services.dao.CategoriesService;
import com.springapp.services.dao.UserCategoriesService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired private CategoriesService categoriesService;
    @Autowired private UserCategoriesService userCategoriesService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> findAll(@RequestParam(value = "justActive", required = false) boolean justActive) {
        return categoriesService.findAll(justActive);
    }

    @RequestMapping(value = "/{catId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable Long catId) {
        Category category = categoriesService.getCategoryById(catId);
        category.removeLinks();
        category.add(linkTo(CategoriesController.class).slash(category.getCatId()).withSelfRel());
        return category;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Category createCategory(@RequestBody Category category) {
        return categoriesService.createCategory(category);
    }

    @RequestMapping(value = "/{catId}/users/{userId}")
    public JSONAware addUserCategory(@PathVariable Long catId, @PathVariable Long userId) {
        return userCategoriesService.addCategory(userId, catId);
    }

    @RequestMapping(value = "/{catId}", method = RequestMethod.PUT)
    public Category updateCategory(@PathVariable Long catId, @RequestBody Category category) {
        return categoriesService.updateCategory(catId, category);
    }

    @RequestMapping(value = "/{catId}", method = RequestMethod.DELETE)
    public JSONAware deleteCategory(@PathVariable Long catId) {
        return categoriesService.deleteCategory(catId);
    }

    @RequestMapping(value = "/{catId}/users/{userId}", method = RequestMethod.DELETE)
    public JSONAware removeUserCategory(@PathVariable Long catId, @PathVariable Long userId) {
        return userCategoriesService.removeCategory(userId, catId);
    }
}
