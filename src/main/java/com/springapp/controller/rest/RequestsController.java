package com.springapp.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/requests")
public class RequestsController {

    /*@Autowired
    private RequestsRepository requestsRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}", method = RequestMethod.GET)
    public Request findById(@PathVariable Long reqId) {

        Request requests = requestsRepository.findByReqId(reqId);

        requests.removeLinks();

        requests.add(linkTo(methodOn(RequestsController.class).getRequestAnswers(reqId)).withRel("answersList"));
        return requests;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{reqId}/answers", method = RequestMethod.GET)
    public List<Response> getRequestAnswers(@PathVariable Long reqId) {
        Request requests = requestsRepository.findByReqId(reqId);
        List<Response> answers = requests.getResponseList();

        for (Response a: answers) {
            a.removeLinks();
            a.add(linkTo(AnswersController.class).slash(a.getAnswId()).withSelfRel());
        }

        return answers;

    }*/

}
