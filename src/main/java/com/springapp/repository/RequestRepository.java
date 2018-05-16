package com.springapp.repository;

import com.springapp.entity.Request;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface RequestRepository extends CrudRepository<Request, Long> {
}
