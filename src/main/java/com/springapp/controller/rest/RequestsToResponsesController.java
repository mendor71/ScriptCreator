package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.StateRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requestToResponse")
public class RequestsToResponsesController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StateRepository stateRepository;

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.POST)
    public ResponseEntity addRequestToResponse(@PathVariable Integer respId, @RequestBody Request request) {
        Response response = responseRepository.findOne(respId);

        if (response == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Response not found by ID: " + respId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (request.getReqId() != null && request.getReqId() != 0) {
            request = requestRepository.findOne(request.getReqId());
        } else {
            request.setReqCategory(response.getRespCategory());

            if (request.getReqState() == null)
                request.setReqState(stateRepository.findByStateName(appProperties.getDefaultState()));

            if (request.getReqPrior() == null)
                request.setReqPrior(0);

            request = requestRepository.save(request);
        }

        response.getChildRequestList().add(request);

        request = requestRepository.save(request);
        responseRepository.save(response);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Request with ID: " + request.getReqId() + " added as child request to response with ID: " + respId), HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.DELETE)
    public ResponseEntity removeRequestFromResponse(@PathVariable Integer respId, @RequestParam(value = "reqId") Integer reqId) {
        Response response = responseRepository.findOne(respId);
        Request request = requestRepository.findOne(reqId);

        if (response == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Response not found by ID: " + respId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (request == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Request not found by ID: " + reqId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (response.getChildRequestList().contains(request)) {
            response.getChildRequestList().remove(request);
            requestRepository.save(request);
            responseRepository.save(response);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Request with ID: " + request.getReqId() + " removed as child request from response with ID: " + respId), HttpStatus.OK);
    }
}
