package com.springapp.controller.rest;

import com.springapp.IncludeAPI;
import com.springapp.entity.Category;
import com.springapp.services.dao.CategoriesService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @IncludeAPI(arguments = "Boolean justActive")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Category> findAll(@RequestParam(value = "justActive", required = false) boolean justActive) {
        return categoriesService.findAll(justActive);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{catId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable Long catId) {
        Category category = categoriesService.getCategoryById(catId);
        category.removeLinks();
        category.add(linkTo(CategoriesController.class).slash(category.getCatId()).withSelfRel());
        return category;
    }

    @IncludeAPI(arguments = {"Category category"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public Category createCategory(@RequestBody Category category) {
        return categoriesService.createCategory(category);
    }

    @IncludeAPI(arguments = {"Category category"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{catId}", method = RequestMethod.PUT)
    public Category updateCategory(@PathVariable Long catId, @RequestBody Category category) {
        return categoriesService.updateCategory(catId, category);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{catId}", method = RequestMethod.DELETE)
    public JSONAware deleteCategory(@PathVariable Long catId) {
        return categoriesService.deleteCategory(catId);
    }
}
