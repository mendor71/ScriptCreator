package com.springapp.services.dao;

import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ResponseRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class ResponsesRelationsService {
    private RequestRepository requestRepository;
    private ResponseRepository responseRepository;
    private RequestsService requestsService;

    @Autowired
    public ResponsesRelationsService(RequestRepository requestRepository, ResponseRepository responseRepository, RequestsService requestsService) {
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.requestsService = requestsService;
    }

    public ResponseEntity addChildRequest(Long respId, Request request) {
        Response response = responseRepository.findOne(respId);

        if (response == null) { return new ResponseEntity(createNotFoundResponse("Ответ не найден по ID: " + respId), HttpStatus.BAD_REQUEST); }

        if (request.getReqId() != null && request.getReqId() != 0) {
            request = requestRepository.findOne(request.getReqId());
        } else {
            request = requestsService.createRequest(request);
        }

        response.getChildRequestList().add(request);
        request.getParentResponseList().add(response);

        request = requestRepository.save(request);
        responseRepository.save(response);

        return new ResponseEntity(request, HttpStatus.OK);
    }

    public JSONAware removeChildRequest(Long respId, Long reqId) {
        Response response = responseRepository.findOne(respId);
        Request request = requestRepository.findOne(reqId);

        if (response == null) { return createNotFoundResponse("Ответ не найден по ID: " + respId); }

        if (request == null) { return createNotFoundResponse("Вопрос не найден по ID: " + respId);  }

        if (response.getChildRequestList().contains(request)) {
            response.getChildRequestList().remove(request);
            requestRepository.save(request);
            responseRepository.save(response);
        }

        return createOKResponse("Связка успешно удалена");
    }
}
