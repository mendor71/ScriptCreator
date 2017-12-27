package com.springapp.controller.rest;

import com.springapp.entity.Category;
import com.springapp.entity.User;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.UserRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/userCategories")
public class UserCategoriesController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addCategory(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "catId") Integer catId) {
        User user = userRepository.findOne(userId);
        Category category = categoryRepository.findOne(catId);

        if (user == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "User not found by ID: " + userId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (category == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Category not found by ID: " + catId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (!user.getUserCategoriesList().contains(category)) {
            user.getUserCategoriesList().add(category);
            userRepository.save(user);
            categoryRepository.save(category);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "category " + category.getCatName() + " added to user " + user.getUserLogin()), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity removeCategory(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "catId") Integer catId) {
        User user = userRepository.findOne(userId);
        Category category = categoryRepository.findOne(catId);

        if (user == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "User not found by ID: " + userId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (category == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Category not found by ID: " + catId), HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.getUserCategoriesList().contains(category)) {
            user.getUserCategoriesList().remove(category);
            userRepository.save(user);
            categoryRepository.save(category);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "category " + category.getCatName() + " removed from user " + user.getUserLogin()), HttpStatus.OK);

    }
}
