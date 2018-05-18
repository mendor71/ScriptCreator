package com.springapp.repository;

import com.springapp.entity.Request;
import com.springapp.entity.Response;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface RequestRepository extends CrudRepository<Request, Long> {
    Iterable<Request> findByReqScenarioScId(Long scId);
    Iterable<Request> findByReqScenarioScIdAndParentResponseListIsNull(Long scId);
    Iterable<Request> findByParentResponseListContaining(@Param("parentResponseList") List<Response> responses);
    Iterable<Request> findByChildResponseListContaining(@Param("childResponseList") List<Response> responses);
}
