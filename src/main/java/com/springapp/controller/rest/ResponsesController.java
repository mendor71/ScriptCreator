package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Category;
import com.springapp.entity.Response;
import com.springapp.entity.State;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.StateRepository;
import com.springapp.util.JSONResponse;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/responses")
public class ResponsesController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StateRepository stateRepository;

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Response> getAllResponses() {
        Iterable<Response> responses = responseRepository.findAll();
        List<Response> responseList = new ArrayList<Response>();

        for (Response r: responses) {
            responseList.add(r);
        }

        return responseList;
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.GET)
    public Response getResponseById(@PathVariable Integer respId) {
        return responseRepository.findOne(respId);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Response createResponse(@RequestBody Response response) {

        if (response.getRespCategory() == null) {
            Category category = categoryRepository.findByCatName(appProperties.getDefaultCategory());
            response.setRespCategory(category);
        }

        if (response.getRespState() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            response.setRespState(state);
        }

        return responseRepository.save(response);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Response updateResponse(@RequestBody Response response) {
        return responseRepository.save(response);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity deleteResponse(@RequestParam(value = "respId") Integer respId) {
        Response response = responseRepository.findOne(respId);

        if (response == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Response not found by ID: " + respId), HttpStatus.NOT_ACCEPTABLE);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        response.setRespState(state);
        responseRepository.save(response);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Response mark as deleted: " + respId), HttpStatus.OK);
    }
}
