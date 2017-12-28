package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Request;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.StateRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/requests")
public class RequestsController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private RequestRepository requestsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StateRepository stateRepository;

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.GET)
    public Request findRequestById(@PathVariable Integer reqId) {
        return requestsRepository.findOne(reqId);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Request createRequest(@RequestBody Request request) {
        if (request.getReqCategory() == null) {
            Category category = categoryRepository.findByCatName(appProperties.getDefaultCategory());
            request.setReqCategory(category);
        }

        if (request.getReqState() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            request.setReqState(state);
        }

        return requestsRepository.save(request);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Request updateRequest(@RequestBody Request request) {
        return requestsRepository.save(request);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteRequest(@RequestParam(value = "reqId") Integer reqId) {
        Request request = requestsRepository.findOne(reqId);

        if (request == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Request not found by ID: " + reqId), HttpStatus.NOT_ACCEPTABLE);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        request.setReqState(state);
        requestsRepository.save(request);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Request mark as deleted: " + reqId), HttpStatus.OK);
    }

}
