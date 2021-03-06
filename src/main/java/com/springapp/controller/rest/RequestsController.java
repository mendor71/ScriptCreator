package com.springapp.controller.rest;


import com.springapp.IncludeAPI;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.services.dao.RequestsService;
import com.springapp.services.dao.RequestsRelationsService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/requests")
public class RequestsController {
    private RequestsService requestsService;
    private RequestsRelationsService requestsRelationsService;

    @Autowired
    public RequestsController(RequestsService requestsService, RequestsRelationsService requestsRelationsService) {
        this.requestsService = requestsService;
        this.requestsRelationsService = requestsRelationsService;
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.GET)
    public Request findRequestById(@PathVariable Long reqId) {
        return requestsService.findRequestById(reqId);
    }

    @IncludeAPI(arguments = {"Boolean kernel","Boolean justActive"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/scenario/{scId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByScenarioId(@PathVariable Long scId
            , @RequestParam(value = "kernel", required = false) boolean kernel
            , @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return requestsService.findRequestsByScenarioId(scId, kernel, justActive == null ? true : justActive);
    }

    @IncludeAPI(arguments = {"Boolean justActive"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/parent_response/{respId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByParentResponseId(@PathVariable Long respId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return requestsService.findByParentResponseId(respId, justActive == null ? true : justActive);
    }

    @IncludeAPI(arguments = {"Boolean justActive"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/child_response/{respId}", method = RequestMethod.GET)
    public Iterable<Request> findRequestsByChildResponseId(@PathVariable Long respId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return requestsService.findByChildResponseId(respId, justActive == null ? true : justActive);
    }

    @IncludeAPI(arguments = {"Request request"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Request createRequest(@RequestBody Request request) {
        return requestsService.createRequest(request);
    }

    @IncludeAPI(arguments = {"Response response"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}/child_response", method = RequestMethod.POST)
    public ResponseEntity addChildResponse(@PathVariable Long reqId, @RequestBody Response response) {
        return requestsRelationsService.addChildResponse(reqId, response);
    }

    @IncludeAPI(arguments = {"Request request"})
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.PUT)
    public Request updateRequest(@PathVariable Long reqId, @RequestBody Request request) {
        return requestsService.updateRequest(reqId, request);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.DELETE)
    public JSONAware deleteRequest(@PathVariable Long reqId) {
        return requestsService.deleteRequest(reqId);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}/child_response/{respId}", method = RequestMethod.DELETE)
    public JSONAware removeChildResponse(@PathVariable Long reqId, @PathVariable Long respId) {
        return requestsRelationsService.removeChildResponse(reqId, respId);
    }

}
