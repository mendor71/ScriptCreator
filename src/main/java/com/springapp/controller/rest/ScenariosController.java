package com.springapp.controller.rest;

import com.springapp.entity.Scenario;
import com.springapp.services.dao.ScenariosService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.springapp.util.JSONResponse.*;

@RestController(value = "/scenarios")
public class ScenariosController {
    @Autowired private ScenariosService scenariosService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Scenario> findAll(@RequestParam(value = "justActive", required = false) boolean justActive) {
        return scenariosService.findAll(justActive);
    }

    @RequestMapping(method = RequestMethod.POST)
    public JSONAware createScenario(@RequestBody Scenario scenario) {
        scenariosService.createScenario(scenario);
        return createOKResponse("Сценарий успешно создан");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public JSONAware updateScenario(@RequestBody Scenario scenario) {
        scenariosService.updateScenario(scenario);
        return createOKResponse("Сценарий успешно обновлен");
    }

    @RequestMapping(value = "/{scId}",method = RequestMethod.DELETE)
    public JSONAware createScenario(@PathVariable Long scId) {
        return scenariosService.deleteScenario(scId);
    }
}
