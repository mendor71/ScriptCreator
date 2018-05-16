package com.springapp.repository;

import com.springapp.entity.State;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface StateRepository extends CrudRepository<State, Long> {
    State findByStateName(String stateName);
}
