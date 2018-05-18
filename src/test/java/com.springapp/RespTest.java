package com.springapp;

import com.springapp.entity.Response;
import com.springapp.services.dao.ResponsesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"})
public class RespTest {
    @Autowired
    private ResponsesService responsesService;

    @Test
    public void testFindByParentRequestListContais() {
        Iterable<Response> responses = responsesService.findResponsesByParentRequestId(7L);
        responses.forEach(System.out::println);
    }
}
