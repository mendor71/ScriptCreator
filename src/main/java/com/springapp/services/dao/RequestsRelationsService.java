package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ResponseRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class RequestsRelationsService {
    @Autowired private RequestRepository requestRepository;
    @Autowired private ResponseRepository responseRepository;

    @Autowired private ResponsesService responsesService;

    public JSONAware addChildResponse(Long reqId, Response response) {
        Request request = requestRepository.findOne(reqId);

        if (request == null) { return createNotFoundResponse("Ответ не найден по ID: " + reqId);}

        if (response.getRespId() != null && response.getRespId() != 0) {
            response = responseRepository.findOne(response.getRespId());
        } else {
            response = responsesService.createResponse(response);
        }

        request.getChildResponseList().add(response);
        responseRepository.save(response);
        requestRepository.save(request);

        return createOKResponse("Связка успешно установлена");
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
