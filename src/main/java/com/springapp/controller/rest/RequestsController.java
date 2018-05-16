package com.springapp.controller.rest;


import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.services.dao.RequestsService;
import com.springapp.services.dao.RequestsRelationsService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createRequest(@RequestBody Request request) {
        requestsService.createRequest(request);
        return createOKResponse("Вопрос успешно создан");
    }

    @RequestMapping(value = "/{reqId}/child_response", method = RequestMethod.POST)
    public JSONAware addChildResponse(@PathVariable Long reqId, @RequestBody Response response) {
        return requestsRelationsService.addChildResponse(reqId, response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateRequest(@RequestBody Request request) {
        requestsService.updateRequest(request);
        return createOKResponse("Вопрос успешно обновлен");
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
