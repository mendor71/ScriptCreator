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
public class RequestsRepository {

    private List<Requests> requests = new ArrayList<Requests>();

    public RequestsRepository() {
        requests.add(
                new Requests(1l
                        , "Добрый день, !ИМЯ!, вам удобно сейчас разговаривать."
                        , new Category(1l, "Бытовая техника")
                        , new State(1l, "Active", "Активен")
                        , new ArrayList<Answers>(Arrays.asList(new Answers(1l
                            , "Нет, идите нахер"
                            , new Category(1l, "Бытовая техника")
                            , new State(1l, "Active", "Активен")
                            , new ArrayList<Requests> ()),
                            new Answers(2l
                            , "Да, внимательно вас слушаю"
                            , new Category(1l, "Бытовая техника")
                            , new State(1l, "Active", "Активен")
                            , new ArrayList<Requests> ())))
                )
        );

        requests.add(
                new Requests(2l
                        , "Извините, всего доброго"
                        , null
                        , new State(1l, "Active", "Активен")
                        , null
                )
        );

        requests.add(
                new Requests(3l
                        , "Сам иди в пень, мудак!"
                        , null
                        , new State(1l, "Active", "Активен")
                        , null
                )
        );
    }

    public List<Requests> findAll() {
        return requests;
    }

    public Requests findByReqId(Long reqId) {
        for (Requests r: requests) {
            if (r.getReqId().equals(reqId)) {
                return  r;
            }
        }
        return null;
    }
}
