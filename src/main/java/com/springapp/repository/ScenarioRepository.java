package com.springapp.repository;

import com.springapp.entity.Scenario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScenarioRepository extends CrudRepository<Scenario, Long> {
    List<Scenario> findScenarioByScStateIdStateName(String stateName);
    List<Scenario> findScenarioByScName(String scName);
    Scenario findFirstByScIdGreaterThan(Long scId);
}
