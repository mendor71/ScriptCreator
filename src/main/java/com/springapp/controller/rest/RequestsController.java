package com.springapp.controller.rest;


import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.services.dao.RequestsService;
import com.springapp.services.dao.RequestsRelationsService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springapp.util.JSONResponse.*;

@RestController
@RequestMapping(value = "/requests")
public class RequestsController {
    @Autowired private RequestsService requestsService;
    @Autowired private RequestsRelationsService requestsRelationsService;

    @RequestMapping(value = "/{reqId}", method = RequestMethod.GET)
    public Request findRequestById(@PathVariable Long reqId) {
        return requestsService.findRequestById(reqId);
    }

    @RequestMapping(value = "/scenario/{scId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByScenarioId(@PathVariable Long scId, @RequestParam(value = "kernel", required = false) boolean kernel) {
        return requestsService.findRequestsByScenarioId(scId, kernel);
    }

    @RequestMapping(value = "/parent_response/{respId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByParentResponseId(@PathVariable Long respId) {
        return requestsService.findByParentResponseId(respId);
    }

    @RequestMapping(value = "/child_response/{respId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByChildResponseId(@PathVariable Long respId) {
        return requestsService.findByChildResponseId(respId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Request createRequest(@RequestBody Request request) {
        return requestsService.createRequest(request);
    }

    @RequestMapping(value = "/{reqId}/child_response", method = RequestMethod.POST)
    public ResponseEntity addChildResponse(@PathVariable Long reqId, @RequestBody Response response) {
        return requestsRelationsService.addChildResponse(reqId, response);
    }

    @RequestMapping(value = "/{reqId}", method = RequestMethod.PUT)
    public Request updateRequest(@PathVariable Long reqId, @RequestBody Request request) {
        return requestsService.updateRequest(reqId, request);
    }

    @RequestMapping(value = "/{reqId}", method = RequestMethod.DELETE)
    public JSONAware deleteRequest(@PathVariable Long reqId) {
        return requestsService.deleteRequest(reqId);
    }

    @RequestMapping(value = "/{reqId}/child_response/{respId}", method = RequestMethod.DELETE)
    public JSONAware removeChildResponse(@PathVariable Long reqId, @PathVariable Long respId) {
        return  requestsRelationsService.removeChildResponse(reqId, respId);
    }

}
