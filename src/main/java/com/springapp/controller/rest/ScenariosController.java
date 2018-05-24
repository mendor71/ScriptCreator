package com.springapp.controller.rest;

import com.springapp.IncludeAPI;
import com.springapp.entity.Scenario;
import com.springapp.services.dao.ScenariosService;
import org.json.simple.JSONAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.springapp.util.JSONResponse.*;

@RestController
@RequestMapping(value = "/scenarios")
public class ScenariosController {
    private ScenariosService scenariosService;

    @Autowired
    public ScenariosController(ScenariosService scenariosService) {
        this.scenariosService = scenariosService;
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Scenario> findAll(@RequestParam(value = "justActive", required = false) boolean justActive) {
        return scenariosService.findAll(justActive);
    }

    @IncludeAPI(arguments = "Scenario scenario")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(method = RequestMethod.POST)
    public Scenario createScenario(@RequestBody Scenario scenario, Authentication authentication) {
        return scenariosService.createScenario(scenario, authentication.getPrincipal().toString());
    }

    @IncludeAPI(arguments = "Scenario scenario")
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{scId}", method = RequestMethod.PUT)
    public Scenario updateScenario(@PathVariable Long scId, @RequestBody Scenario scenario) {
        return scenariosService.updateScenario(scId, scenario);
    }

    @IncludeAPI
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/{scId}",method = RequestMethod.DELETE)
    public JSONAware createScenario(@PathVariable Long scId) {
        return scenariosService.deleteScenario(scId);
    }
}
