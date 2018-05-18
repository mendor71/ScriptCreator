package com.springapp;

import com.springapp.services.dao.RequestsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
public class ReqTest {
    @Autowired
    private RequestsService requestsService;

    @Test
    public void testListContainingCondition() {
        System.out.println(requestsService.findByParentResponseId(7L));
    }

}
