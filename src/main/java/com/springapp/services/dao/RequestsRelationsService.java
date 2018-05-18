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
public class RequestsRelationsService {
    @Autowired private RequestRepository requestRepository;
    @Autowired private ResponseRepository responseRepository;

    @Autowired private ResponsesService responsesService;

    public ResponseEntity addChildResponse(Long reqId, Response response) {
        Request request = requestRepository.findOne(reqId);

        if (request == null) { return new ResponseEntity(createNotFoundResponse("Ответ не найден по ID: " + reqId), HttpStatus.BAD_REQUEST);}

        if (response.getRespId() != null && response.getRespId() != 0) {
            response = responseRepository.findOne(response.getRespId());
        } else {
            response = responsesService.createResponse(response);
        }

        request.getChildResponseList().add(response);
        response = responseRepository.save(response);
        requestRepository.save(request);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    public JSONAware removeChildResponse(Long reqId, Long respId) {
        Request request = requestRepository.findOne(reqId);
        Response response = responseRepository.findOne(respId);

        if (response == null) { return createNotFoundResponse("Ответ не найден по ID: " + respId); }
        if (request == null) { return createNotFoundResponse("Вопрос не найден по ID: " + reqId); }

        if (request.getChildResponseList().contains(response)) {
            request.getChildResponseList().remove(response);
            responseRepository.save(response);
            requestRepository.save(request);
        }

        return createOKResponse("Связка успешно установлена");
    }

}
