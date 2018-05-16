package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.controller.rest.CategoriesController;
import com.springapp.entity.Category;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static com.springapp.util.JSONResponse.*;

@Service
public class CategoriesService {
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private StateRepository stateRepository;
    @Autowired private AppProperties appProperties;

    public List<Category> findAll() {
        Iterable<Category> categories = categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<Category>();
        for (Category c: categories) {
            categoryList.add(c);
        }
        return categoryList;
    }

    public Category getCategoryById(Long catId) {
        return categoryRepository.findOne(catId);
    }

    public JSONAware createCategory(Category category) {
        if (category.getCatStateId() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            category.setCatStateId(state);
        }

        categoryRepository.save(category);
        return createOKResponse("Категория успешно создана!");
    }

    public JSONAware updateCategory(Category category) {
        Category dbCat = categoryRepository.findOne(category.getCatId());
        if (!category.getCatStateId().equals(dbCat.getCatStateId()))
            dbCat.setCatStateId(stateRepository.findOne(category.getCatStateId().getStateId()));
        if (!category.getCatName().equals(dbCat.getCatName()))
            dbCat.setCatName(category.getCatName());

        return createOKResponse("Категория успешно изменена!");
    }

    public JSONAware deleteCategory(Long catId) {
        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());

        Category category = categoryRepository.findOne(catId);

        if (category == null) {
            return createNotFoundResponse("Категория не найден по ID: " + catId);
        }

        category.setCatStateId(state);
        categoryRepository.save(category);

        return createOKResponse("Категория помечена как удаленная, ID: " + catId);
    }

}
