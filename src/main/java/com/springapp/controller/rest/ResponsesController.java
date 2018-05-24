package com.springapp.controller.rest;

import com.springapp.IncludeAPI;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.services.dao.ResponsesRelationsService;
import com.springapp.services.dao.ResponsesService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springapp.util.JSONResponse.*;

@RestController
@RequestMapping(value = "/responses")
public class ResponsesController {
    private ResponsesService responsesService;
    private ResponsesRelationsService responsesRelationsService;

    @Autowired
    public ResponsesController(ResponsesService responsesService, ResponsesRelationsService responsesRelationsService) {
        this.responsesService = responsesService;
        this.responsesRelationsService = responsesRelationsService;
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.GET)
    public Response findResponseById(@PathVariable Long respId) {
        return responsesService.findResponseById(respId);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/scenario/{scId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByScenarioId(@PathVariable Long scId) {
        return responsesService.findResponsesByScenarioId(scId);
    }

    @IncludeAPI(arguments = "Boolean justActive")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/parent_request/{reqId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByParentRequestId(@PathVariable Long reqId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return responsesService.findResponsesByParentRequestId(reqId, justActive == null ? true : justActive);
    }

    @IncludeAPI(arguments = "Boolean justActive")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/child_request/{reqId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByChildRequestId(@PathVariable Long reqId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return responsesService.findResponsesByChildRequestId(reqId, justActive == null ? true : justActive);
    }

    @IncludeAPI(arguments = "Response response")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Response createResponse(@RequestBody Response response) {
        return responsesService.createResponse(response);
    }

    @IncludeAPI(arguments = "Request request")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}/child_request", method = RequestMethod.POST)
    public ResponseEntity addChildRequest(@PathVariable Long respId, @RequestBody Request request) {
        return responsesRelationsService.addChildRequest(respId, request);
    }

    @IncludeAPI(arguments = "Response response")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.PUT)
    public Response updateResponse(@PathVariable Long respId, @RequestBody Response response) {
        return responsesService.updateResponse(respId, response);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.DELETE)
    public JSONAware deleteResponse(@PathVariable Long respId) {
        return responsesService.deleteResponse(respId);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}/child_request/{reqId}", method = RequestMethod.DELETE)
    public JSONAware removeChildRequest(@PathVariable Long respId, @PathVariable Long reqId) {
        return responsesRelationsService.removeChildRequest(respId, reqId);
    }
}
