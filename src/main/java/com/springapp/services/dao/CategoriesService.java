package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.springapp.util.JSONResponse.*;

@Service
public class CategoriesService {
    private CategoryRepository categoryRepository;
    private StateRepository stateRepository;
    private AppProperties appProperties;

    @Autowired
    public CategoriesService(CategoryRepository categoryRepository, StateRepository stateRepository, AppProperties appProperties) {
        this.categoryRepository = categoryRepository;
        this.stateRepository = stateRepository;
        this.appProperties = appProperties;
    }

    public Iterable<Category> findAll(boolean justActive) {
        if (justActive)
            return categoryRepository.findByCatStateIdStateName(appProperties.getDefaultState());
        else
            return categoryRepository.findAll();
    }

    public Category getCategoryById(Long catId) {
        return categoryRepository.findOne(catId);
    }

    public Category createCategory(Category category) {
        if (category.getCatStateId() != null && category.getCatStateId().getStateId() != null)
            category.setCatStateId(stateRepository.findOne(category.getCatStateId().getStateId()));
        else
            category.setCatStateId(stateRepository.findByStateName(appProperties.getDefaultState()));

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long catId, Category category) {
        Category dbCat = categoryRepository.findOne(catId);
        if (category.getCatStateId() != null
                && category.getCatStateId().getStateId() != null
                && !category.getCatStateId().equals(dbCat.getCatStateId()))
                    dbCat.setCatStateId(stateRepository.findOne(category.getCatStateId().getStateId()));
        if (!category.getCatName().equals(dbCat.getCatName()))
            dbCat.setCatName(category.getCatName());

        return categoryRepository.save(dbCat);
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
