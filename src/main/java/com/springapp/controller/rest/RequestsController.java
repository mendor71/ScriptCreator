package com.springapp.controller.rest;

import com.springapp.entity.Answers;
import com.springapp.entity.Requests;
import com.springapp.service.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@RequestMapping(value = "/requests")
public class RequestsController {

    @Autowired
    private RequestsRepository requestsRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.GET)
    public Requests findById(@PathVariable Long reqId) {

        Requests requests = requestsRepository.findByReqId(reqId);

        requests.removeLinks();

        requests.add(linkTo(methodOn(RequestsController.class).getRequestAnswers(reqId)).withRel("answersList"));
        return requests;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}/answers", method = RequestMethod.GET)
    public List<Answers> getRequestAnswers(@PathVariable Long reqId) {
        Requests requests = requestsRepository.findByReqId(reqId);
        List<Answers> answers = requests.getAnswersList();

        for (Answers a: answers) {
            a.removeLinks();
            a.add(linkTo(AnswersController.class).slash(a.getAnswId()).withSelfRel());
        }

        return answers;

    }

}
