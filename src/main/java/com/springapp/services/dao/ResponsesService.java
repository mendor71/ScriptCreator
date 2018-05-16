package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Response;
import com.springapp.entity.State;
import com.springapp.repository.ResponseRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class ResponsesService {

    @Autowired private AppProperties appProperties;
    @Autowired private ResponseRepository responseRepository;
    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private StateRepository stateRepository;

    public Iterable<Response> getAllResponses() {
         return responseRepository.findAll();
    }

    public Response getResponseById(Long respId) {
        return responseRepository.findOne(respId);
    }

    public Response createResponse(Response response) {
        if (response.getRespScenario() == null) { response.setRespScenario(scenarioRepository.findOne(response.getRespScenario().getScId())); }
        if (response.getRespState() == null) {
            State state = stateRepository.findByStateName(appProperties.getDefaultState());
            response.setRespState(state);
        }

        return responseRepository.save(response);
    }

    public Response updateResponse(Response response) {
        return responseRepository.save(response);
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
