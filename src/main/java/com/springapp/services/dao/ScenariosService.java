package com.springapp.services.dao;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Scenario;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springapp.util.JSONResponse.*;

@Service
public class ScenariosService {
    @Autowired private ScenarioRepository scenarioRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private StateRepository stateRepository;
    @Autowired private AppProperties appProperties;

    public Iterable<Scenario> findAll(boolean justActive) {
        if (justActive)
            return scenarioRepository.findScenarioByScStateIdStateName(appProperties.getDefaultState());
        else
            return scenarioRepository.findAll();
    }

    public Scenario findByScId(Long scId) {
        return scenarioRepository.findOne(scId);
    }

    public Scenario createScenario(Scenario scenario) {
        scenario.setScStateId(scenario.getScStateId() != null
                ? stateRepository.findOne(scenario.getScStateId().getStateId())
                : stateRepository.findByStateName(appProperties.getDefaultState()));
        scenario.setScCatId(scenario.getScCatId() != null
                ? categoryRepository.findOne(scenario.getScCatId().getCatId())
                : null);
        return scenarioRepository.save(scenario);
    }

    public Scenario updateScenario(Scenario scenario) {
        return scenarioRepository.save(scenario);
    }

    public JSONAware deleteScenario(Long scId) {
        Scenario scenario = scenarioRepository.findOne(scId);
        if (scenario == null) { return createNotFoundResponse("Сценарий не найден по ID"); }

        scenario.setScStateId(stateRepository.findByStateName(appProperties.getDefaultDeletedState()));
        return createOKResponse("Сценарий " + scId + " помечен как удаленный");
    }

}
