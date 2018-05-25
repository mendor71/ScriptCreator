package com.springapp;

import com.springapp.entity.Scenario;
import com.springapp.repository.CategoryRepository;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import com.springapp.services.dao.ScenariosService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/java/com.springapp/test-application-context.xml"})
public class ScenarioServiceTest {
    @Autowired private ScenariosService scenariosService;
    @Autowired private StateRepository stateRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ScenarioRepository scenarioRepository;

    private Scenario scenario;

    @Before
    public void preSet() {
        scenario = new Scenario();
        scenario.setScStateId(stateRepository.findOne(2L));
        scenario.setScName("RANDOM_TEST_NAME");
    }

    @Test
    public void testCreate() {
        scenario = scenariosService.createScenario(scenario, null);
        assertNotNull(scenario.getScId());
    }

    @Test
    public void testUpdate() {
        scenario.setScCatId(categoryRepository.findOne(2L));
        scenario = scenariosService.updateScenario(2L, scenario);
        assertNotNull(scenario.getScId());
    }

    @Test
    public void testFindScenarioAndDeleteThem() {
        scenario = scenarioRepository.findFirstByScIdGreaterThan(2L);
        assertNotNull(scenario);
        JSONObject response = (JSONObject) scenariosService.deleteScenario(scenario.getScId());
        assertEquals(response.get("state").toString(), "OK");
    }
}
