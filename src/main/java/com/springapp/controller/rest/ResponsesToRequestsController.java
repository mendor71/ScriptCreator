package com.springapp.controller.rest;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.StateRepository;
import com.springapp.util.JSONResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/responseToRequest")
public class ResponsesToRequestsController {

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private StateRepository stateRepository;

    @RequestMapping(value = "/{reqId}", method = RequestMethod.POST)
    public ResponseEntity addResponseToRequest(@PathVariable Integer reqId, @RequestBody Response response) {
        Request request = requestRepository.findOne(reqId);

        if (request == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Request not found by ID: " + reqId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (response.getRespId() != null && response.getRespId() != 0) {
            response = responseRepository.findOne(response.getRespId());
        } else {
            response.setRespCategory(request.getReqCategory());

            if (response.getRespState() == null)
                response.setRespState(stateRepository.findByStateName(appProperties.getDefaultState()));

            if (response.getRespPrior() == 0)
                response.setRespPrior(0);

            responseRepository.save(response);
        }

        request.getChildResponseList().add(response);
        response = responseRepository.save(response);
        requestRepository.save(request);

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Response with ID: " + response.getId() + " added as child response to request with ID: " + reqId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{reqId}", method = RequestMethod.DELETE)
    public ResponseEntity removeResponseFromRequest(@PathVariable Integer reqId, @RequestParam(value = "respId") Integer respId) {
        Request request = requestRepository.findOne(reqId);
        Response response = responseRepository.findOne(respId);

        if (response == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Response not found by ID: " + respId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (request == null) {
            return new ResponseEntity(new JSONResponse(JSONResponse.STATE.NOT_ACCEPTABLE_DATA, "Request not found by ID: " + reqId) , HttpStatus.NOT_ACCEPTABLE);
        }

        if (request.getChildResponseList().contains(response)) {
            request.getChildResponseList().add(response);
            response = responseRepository.save(response);
            requestRepository.save(request);
        }

        return new ResponseEntity(new JSONResponse(JSONResponse.STATE.OK, "Response with ID: " + response.getId() + " removed as child response from request with ID: " + reqId), HttpStatus.OK);
    }
}
