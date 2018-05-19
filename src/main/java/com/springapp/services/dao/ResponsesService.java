package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Request;
import com.springapp.entity.Response;
import com.springapp.entity.State;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.springapp.util.JSONResponse.*;

@Service
public class ResponsesService {

    @Autowired private AppProperties appProperties;
    @Autowired private ResponseRepository responseRepository;
    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private StateRepository stateRepository;

    @Autowired private RequestsService requestsService;

    public Iterable<Response> findAllResponses() {
         return responseRepository.findAll();
    }

    public Response findResponseById(Long respId) {
        return responseRepository.findOne(respId);
    }

    public Iterable<Response> findResponsesByScenarioId(Long scId) {
        return responseRepository.findByRespScenarioScId(scId);
    }

    public Iterable<Response> findResponsesByParentRequestId(Long reqId, boolean justActive) {
        List<Request> requests = Collections.singletonList(requestsService.findRequestById(reqId));

        if (justActive)
            return responseRepository.findByParentRequestListContainingAndRespStateStateId(requests, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
        else
            return responseRepository.findByParentRequestListContaining(requests);
    }

    public Iterable<Response> findResponsesByChildRequestId(Long reqId, boolean justActive) {
        List<Request> requests = Collections.singletonList(requestsService.findRequestById(reqId));

        if (justActive)
            return responseRepository.findByChildRequestListContainingAndRespStateStateId(requests, stateRepository.findByStateName(appProperties.getDefaultState()).getStateId());
        else
            return responseRepository.findByChildRequestListContaining(requests);
    }

    public Response createResponse(Response response) {
        if (response.getRespScenario() == null && response.getRespScenario().getScId() != null)
            response.setRespScenario(scenarioRepository.findOne(response.getRespScenario().getScId()));

        if (response.getRespState() != null && response.getRespState().getStateId() != null)
            response.setRespState(stateRepository.findOne(response.getRespState().getStateId()));
        else
            response.setRespState(stateRepository.findByStateName(appProperties.getDefaultState()));

        if (response.getRespPrior() == null)
            response.setRespPrior(0);

        return responseRepository.save(response);
    }

    public Response updateResponse(Long respId, Response response) {
        Response dbResp = responseRepository.findOne(respId);

        if (response.getRespScenario() != null && response.getRespScenario().getScId() != null
                && (dbResp.getRespScenario() == null || !dbResp.getRespScenario().equals(response.getRespScenario())))
            dbResp.setRespScenario(scenarioRepository.findOne(response.getRespScenario().getScId()));

        if (response.getRespState() != null && response.getRespState().getStateId() != null
                && (dbResp.getRespState() == null || !dbResp.getRespState().equals(response.getRespState())))
            dbResp.setRespState(stateRepository.findOne(response.getRespState().getStateId()));

        if (response.getRespText() != null && !dbResp.getRespText().equals(response.getRespText()))
            dbResp.setRespText(response.getRespText());

        if (response.getRespPrior() != null && !dbResp.getRespPrior().equals(response.getRespPrior()))
            dbResp.setRespPrior(response.getRespPrior());

        return responseRepository.save(dbResp);
    }

    public JSONAware deleteResponse(Long respId) {
        Response response = responseRepository.findOne(respId);

        if (response == null) {
            return createNotFoundResponse("Ответ не найден по ID: " + respId);
        }

        State state = stateRepository.findByStateName(appProperties.getDefaultDeletedState());
        response.setRespState(state);
        responseRepository.save(response);

        return createOKResponse("Ответ помечен как удаленный");
    }

}
