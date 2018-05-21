package com.springapp.controller.rest;

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
    @Autowired private ResponsesService responsesService;
    @Autowired private ResponsesRelationsService responsesRelationsService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.GET)
    public Response findResponseById(@PathVariable Long respId) {
        return responsesService.findResponseById(respId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/scenario/{scId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByScenarioId(@PathVariable Long scId) {
        return responsesService.findResponsesByScenarioId(scId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/parent_request/{reqId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByParentRequestId(@PathVariable Long reqId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return responsesService.findResponsesByParentRequestId(reqId, justActive == null ? true : justActive);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/child_request/{reqId}", method = RequestMethod.GET)
    public Iterable<Response> findResponsesByChildRequestId(@PathVariable Long reqId, @RequestParam(value = "justActive", required = false) Boolean justActive) {
        return responsesService.findResponsesByChildRequestId(reqId, justActive == null ? true : justActive);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Response createResponse(@RequestBody Response response) {
        return responsesService.createResponse(response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}/child_request", method = RequestMethod.POST)
    public ResponseEntity addChildRequest(@PathVariable Long respId, @RequestBody Request request) {
        return responsesRelationsService.addChildRequest(respId, request);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.PUT)
    public Response updateResponse(@PathVariable Long respId, @RequestBody Response response) {
        return responsesService.updateResponse(respId, response);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}", method = RequestMethod.DELETE)
    public JSONAware deleteResponse(@PathVariable Long respId) {
        return responsesService.deleteResponse(respId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{respId}/child_request/{reqId}", method = RequestMethod.DELETE)
    public JSONAware removeChildRequest(@PathVariable Long respId, @PathVariable Long reqId) {
        return responsesRelationsService.removeChildRequest(respId, reqId);
    }
}
