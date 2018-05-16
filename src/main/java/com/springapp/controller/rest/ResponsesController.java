package com.springapp.controller.rest;

import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.services.dao.ResponsesRelationsService;
import com.springapp.services.dao.ResponsesService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.springapp.util.JSONResponse.*;

@RestController
@RequestMapping(value = "/responses")
public class ResponsesController {
    @Autowired private ResponsesService responsesService;
    @Autowired private ResponsesRelationsService responsesRelationsService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Response> getAllResponses() {
        return responsesService.getAllResponses();
    }

    @RequestMapping(value = "/{respId}", method = RequestMethod.GET)
    public Response getResponseById(@PathVariable Long respId) {
        return responsesService.getResponseById(respId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createResponse(@RequestBody Response response) {
        responsesService.createResponse(response);
        return createOKResponse("Ответ успешно создан");
    }

    @RequestMapping(value = "/{respId}/child_request", method = RequestMethod.POST)
    public JSONAware addChildRequest(@PathVariable Long respId, @RequestBody Request request) {
        return responsesRelationsService.addChildRequest(respId, request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateResponse(@RequestBody Response response) {
        responsesService.updateResponse(response);
        return createOKResponse("Ответ успешно обновлен");
    }

    @RequestMapping(value = "/{respId}", method = RequestMethod.DELETE)
    public JSONAware deleteResponse(@PathVariable Long respId) {
        return responsesService.deleteResponse(respId);
    }

    @RequestMapping(value = "/{respId}/child_request/{reqId}", method = RequestMethod.DELETE)
    public JSONAware removeChildRequest(@PathVariable Long respId, @PathVariable Long reqId) {
        return responsesRelationsService.removeChildRequest(respId, reqId);
    }
}
