package com.springapp.services.dao;

import com.springapp.entity.Category;
import com.springapp.entity.User;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.UserRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import static com.springapp.util.JSONResponse.*;

@Service
public class UserCategoriesService {
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    public JSONAware addCategory(Long userId, Long catId) {
        User user = userRepository.findOne(userId);
        Category category = categoryRepository.findOne(catId);

        if (user == null) {createNotFoundResponse("Пользователь не найден по ID: " + userId); }
        if (category == null) {createNotFoundResponse("Категория не найдена по ID: " + catId); }

        if ( !user.getUserCategoriesList().contains(category)) {
            user.getUserCategoriesList().add(category);
            userRepository.save(user);
            categoryRepository.save(category);
        }

        return createOKResponse("Категория добавлена в список доступных пользователю");
    }

    public JSONAware removeCategory(Long userId, Long catId) {
        User user = userRepository.findOne(userId);
        Category category = categoryRepository.findOne(catId);

        if (user == null) {createNotFoundResponse("Пользователь не найден по ID: " + userId); }
        if (category == null) {createNotFoundResponse("Категория не найдена по ID: " + catId); }

        if (user.getUserCategoriesList().contains(category)) {
            user.getUserCategoriesList().remove(category);
            userRepository.save(user);
            categoryRepository.save(category);
        }

        return createOKResponse("Категория удалена из списка доступных пользователю");
    }
}
