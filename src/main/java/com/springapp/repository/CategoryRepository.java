package com.springapp.repository;

import com.springapp.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByCatName(String catName);
    List<Category> findByCatStateIdStateName(String stateName);
}
