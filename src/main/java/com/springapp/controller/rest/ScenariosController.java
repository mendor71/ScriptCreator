package com.springapp.controller.rest;

import com.springapp.entity.Scenario;
import com.springapp.services.dao.ScenariosService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.springapp.util.JSONResponse.*;

@RestController
@RequestMapping(value = "/scenarios")
public class ScenariosController {
    @Autowired private ScenariosService scenariosService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Scenario> findAll(@RequestParam(value = "justActive", required = false) boolean justActive) {
        return scenariosService.findAll(justActive);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Scenario createScenario(@RequestBody Scenario scenario) {
        return scenariosService.createScenario(scenario);
    }

    @RequestMapping(value = "/{scId}", method = RequestMethod.PUT)
    public Scenario updateScenario(@PathVariable Long scId, @RequestBody Scenario scenario) {
        return scenariosService.updateScenario(scId, scenario);
    }

    @RequestMapping(value = "/{scId}",method = RequestMethod.DELETE)
    public JSONAware createScenario(@PathVariable Long scId) {
        return scenariosService.deleteScenario(scId);
    }
}
