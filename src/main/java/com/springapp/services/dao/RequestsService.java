package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Request;
import com.springapp.entity.State;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class RequestsService {
    @Autowired private RequestRepository requestRepository;
    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private StateRepository stateRepository;
    @Autowired private AppProperties appProperties;

    public Request findRequestById(Long reqId) {
        return requestRepository.findOne(reqId);
    }

    public Request createRequest(Request request) {
        if (request.getReqScenario() == null) { request.setReqScenario(scenarioRepository.findOne(request.getReqScenario().getScId())); }
        if (request.getReqState() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            request.setReqState(state);
        }

        return requestRepository.save(request);
    }

    public Request updateRequest(Request request) {
        return requestRepository.save(request);
    }

    public JSONAware deleteRequest(Long reqId) {
        Request request = requestRepository.findOne(reqId);

        if (request == null) {
            return createNotFoundResponse("Вопрос не найден по ID: " + reqId);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        request.setReqState(state);
        requestRepository.save(request);

        return createOKResponse("Вопрос помечен как удаленный");
    }

}
