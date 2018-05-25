package com.springapp;

import com.springapp.entity.Request;
import com.springapp.entity.Scenario;
import com.springapp.entity.State;
import com.springapp.repository.ScenarioRepository;
import com.springapp.repository.StateRepository;
import com.springapp.services.dao.RequestsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/test/java/com.springapp/test-application-context.xml"})
public class RequestServiceTest {
    @Autowired private RequestsService requestsService;
    @Autowired private StateRepository stateRepository;
    @Autowired private ScenarioRepository scenarioRepository;

    private Request requestToCreate;

    @Before
    public void preSet() {
        requestToCreate = new Request();
        requestToCreate.setReqText("test Request");
        requestToCreate.setReqPrior(0);
        requestToCreate.setReqState(null);
        requestToCreate.setReqScenario(null);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateRequestWithoutScenarioIdThrowsDataIntegrityException() {
        requestToCreate = requestsService.createRequest(requestToCreate);
    }

    @Test
    public void testLoadKernelScenarioRequestList() {
        Iterable<Request> kernelRequest = requestsService.findRequestsByScenarioId(1L, true, false);
        assertNotNull(kernelRequest);
    }
}
