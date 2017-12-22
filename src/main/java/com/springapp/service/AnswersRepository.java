package com.springapp.service;

import com.springapp.entity.Answers;
import com.springapp.entity.Category;
import com.springapp.entity.Requests;
import com.springapp.entity.State;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AnswersRepository {
    private List<Answers> answers = new ArrayList<Answers>();

    public AnswersRepository() {


        answers.add(new Answers(1l
                , "Нет, идите нахер"
                , new Category(1l, "Бытовая техника")
                , new State(1l, "Active", "Активен")
                , new ArrayList<Requests> (

                Arrays.asList(new Requests(2l
                        , "Извините, всего доброго"
                        , null
                        , new State(1l, "Active", "Активен")
                        , null
                ),
                        new Requests(3l
                                , "Сам иди в пень, мудак!"
                                , null
                                , new State(1l, "Active", "Активен")
                                , null
                        ))
        )));

    }

    public List<Answers> findAll() {
        return answers;
    }

    public Answers findByAnswId(Long answId) {
        for (Answers a : answers) {
            if (a.getAnswId().equals(answId)) {
                return a;
            }
        }
        return null;
    }
}
