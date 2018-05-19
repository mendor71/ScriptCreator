package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.entity.State;
import com.springapp.repository.RequestRepository;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.springapp.util.JSONResponse.*;

@Service
public class RequestsService {
    @Autowired private RequestRepository requestRepository;
    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private StateRepository stateRepository;
    @Autowired private AppProperties appProperties;

    @Autowired private ResponsesService responsesService;

    public Request findRequestById(Long reqId) {
        return requestRepository.findOne(reqId);
    }

    public Iterable<Request> findRequestsByScenarioId(Long scId, boolean kernel, boolean justActive) {
        if (!kernel)
            if (justActive)
                return requestRepository.findByReqScenarioScIdAndReqStateStateId(scId, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
            else
                return requestRepository.findByReqScenarioScId(scId);
        else
            if (justActive)
                return requestRepository.findByReqScenarioScIdAndReqStateStateIdAndParentResponseListIsNull(scId, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
            else
                return requestRepository.findByReqScenarioScIdAndParentResponseListIsNull(scId);
    }

    public Iterable<Request> findByParentResponseId(Long respId, boolean justActive) {
        List<Response> list = Collections.singletonList(responsesService.findResponseById(respId));
        if (justActive)
            return requestRepository.findByParentResponseListContainingAndReqStateStateId(list, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
        else
            return requestRepository.findByParentResponseListContaining(list);
    }

    public Iterable<Request> findByChildResponseId(Long respId, boolean justActive) {
        List<Response> list = Collections.singletonList(responsesService.findResponseById(respId));
        if (justActive)
            return requestRepository.findByChildResponseListContainingAndReqStateStateId(list, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
        else
            return requestRepository.findByChildResponseListContaining(list);
    }

    public Request createRequest(Request request) {
        if (request.getReqScenario() != null && request.getReqScenario().getScId() != null)
            request.setReqScenario(scenarioRepository.findOne(request.getReqScenario().getScId()));

        if (request.getReqState() != null && request.getReqState().getStateId() != null)
            request.setReqState(stateRepository.findOne(request.getReqState().getStateId()));
        else
            request.setReqState(stateRepository.findByStateName(appProperties.getDefaultState()));

        if (request.getReqPrior() == null)
            request.setReqPrior(0);

        return requestRepository.save(request);
    }

    public Request updateRequest(Long reqId, Request request) {
        Request dbRequest = requestRepository.findOne(reqId);

        if (request.getReqScenario() != null && request.getReqScenario().getScId() != null
                && (dbRequest.getReqScenario() == null || !dbRequest.getReqScenario().equals(request.getReqScenario())))
            dbRequest.setReqScenario(scenarioRepository.findOne(request.getReqScenario().getScId()));

        if (request.getReqState() != null && request.getReqState().getStateId() != null
                && (dbRequest.getReqState() == null || !dbRequest.getReqState().equals(request.getReqState())))
            dbRequest.setReqState(stateRepository.findOne(request.getReqState().getStateId()));

        if (request.getReqText() != null && !request.getReqText().equals(dbRequest.getReqText()))
            dbRequest.setReqText(request.getReqText());

        if (request.getReqPrior() != null && !request.getReqPrior().equals(dbRequest.getReqPrior()))
            dbRequest.setReqPrior(request.getReqPrior());

        return requestRepository.save(dbRequest);
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
