package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{catId}", method = RequestMethod.GET)
    public Category getCategoryById(@PathVariable Integer catId) {
        Category category = categoryRepository.findOne(catId);

        category.removeLinks();
        category.add(linkTo(CategoriesController.class).slash(category.getCatId()).withSelfRel());

        return category;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Category createNew(@RequestBody Category category) {
        if (category.getCatStateId() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            category.setCatStateId(state);
        }
        return categoryRepository.save(category);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Category update(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

}
