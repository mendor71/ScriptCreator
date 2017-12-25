package com.springapp.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/answers")
public class AnswersController {

    /*@Autowired
    private AnswersRepository answersRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{answId}", method = RequestMethod.GET, produces = "application/hal+json")
    public Response findById(@PathVariable Long answId) {
        Response a = answersRepository.findByAnswId(answId);
        a.removeLinks();
        a.add(linkTo(methodOn(AnswersController.class).getAnswerRequests(answId)).withRel("requestsList"));

        return a;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{answId}/requests", method = RequestMethod.GET, produces = "application/hal+json")
    public List<Request> getAnswerRequests(@PathVariable Long answId) {
        Response a = answersRepository.findByAnswId(answId);
        List<Request> requests = a.getRequestsList();

        for (Request r: requests) {
            r.removeLinks();
            r.add(linkTo(RequestsController.class).slash(r.getReqId()).withSelfRel());
        }

        return requests;
    }*/
}
