package com.springapp.controller.rest;

import com.springapp.entity.Answers;
import com.springapp.entity.Requests;
import com.springapp.service.AnswersRepository;
import com.sun.tools.internal.ws.processor.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/answers")
public class AnswersController {

    @Autowired
    private AnswersRepository answersRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{answId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Answers> findById(@PathVariable Long answId) {
        Answers a = answersRepository.findByAnswId(answId);
        a.removeLinks();

        a.add(linkTo(methodOn(AnswersController.class).getAnswerRequests(answId)).withRel("requestsList"));

        return new Resource<Answers>(a);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{answId}/requests", method = RequestMethod.GET)
    public Resource getAnswerRequests(@PathVariable Long answId) {
        Answers a = answersRepository.findByAnswId(answId);
        List<Requests> requests = a.getRequestsList();

        for (Requests r: requests) {
            r.removeLinks();
            r.add(linkTo(RequestsController.class).slash(r.getReqId()).withSelfRel());
        }

        return new Resource(requests);
    }
}
