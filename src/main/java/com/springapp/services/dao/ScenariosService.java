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
        if (scenario.getScStateId() != null && scenario.getScStateId().getStateId() != null)
            scenario.setScStateId(stateRepository.findOne(scenario.getScStateId().getStateId()));
        else
            scenario.setScStateId(stateRepository.findByStateName(appProperties.getDefaultState()));

        if (scenario.getScCatId() != null && scenario.getScCatId().getCatId() != null)
            scenario.setScCatId(categoryRepository.findOne(scenario.getScCatId().getCatId()));
        else
            scenario.setScCatId(null);
        return scenarioRepository.save(scenario);
    }

    public Scenario updateScenario(Long scId, Scenario scenario) {
        Scenario dbSc = scenarioRepository.findOne(scId);
        if (scenario.getScStateId() != null && scenario.getScStateId().getStateId() != null && !scenario.getScStateId().equals(dbSc.getScStateId()))
            dbSc.setScStateId(stateRepository.findOne(scenario.getScStateId().getStateId()));

        if (scenario.getScCatId() != null && scenario.getScCatId().getCatId() != null && (dbSc.getScCatId() == null || !scenario.getScCatId().equals(dbSc.getScCatId())))
            dbSc.setScCatId(categoryRepository.findOne(scenario.getScCatId().getCatId()));

        dbSc.setScName(scenario.getScName() != null ? scenario.getScName() : dbSc.getScName());
        return scenarioRepository.save(dbSc);
    }

    public JSONAware deleteScenario(Long scId) {
        Scenario scenario = scenarioRepository.findOne(scId);
        if (scenario == null) { return createNotFoundResponse("Сценарий не найден по ID"); }

        scenario.setScStateId(stateRepository.findByStateName(appProperties.getDefaultDeletedState()));
        scenarioRepository.save(scenario);
        return createOKResponse("Сценарий " + scId + " помечен как удаленный");
    }

}
