package com.springapp.repository;

import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mendor on 25.12.2017.
 */
public interface ResponseRepository extends CrudRepository<Response, Long> {

    Iterable<Response> findByParentRequestListContaining(@Param("parentRequestList") List<Request> requests);
    Iterable<Response> findByParentRequestListContainingAndRespStateStateId(@Param("parentRequestList") List<Request> requests, @Param("stateId") Long stateId);

    Iterable<Response> findByChildRequestListContaining(@Param("childRequestList") List<Request> requests);
    Iterable<Response> findByChildRequestListContainingAndRespStateStateId(@Param("childRequestList") List<Request> requests, @Param("stateId") Long stateId);

    Iterable<Response> findByRespScenarioScId(Long scId);
    Iterable<Response> findByRespScenarioScIdAndRespStateStateId(Long scId, Long stateId);
}
