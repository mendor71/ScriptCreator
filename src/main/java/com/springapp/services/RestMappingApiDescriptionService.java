package com.springapp.services;

import com.springapp.util.RestControllerApiDescription;
import com.springapp.util.RestMethodApiDescription;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RestMappingApiDescriptionService {

    private Map<String, RestControllerApiDescription> dictionaryMap = new HashMap<>();

    public void addRequestMappingDescription(String url, String method, String actionName, String modelName, String params){
        RestMethodApiDescription description = new RestMethodApiDescription(url, method, actionName, modelName, params);
        if (dictionaryMap.containsKey(description.getModelName())) {
            dictionaryMap.get(description.getModelName()).getMethodList().add(description);
        } else {
            List<RestMethodApiDescription> descriptions = new ArrayList<>();
            descriptions.add(description);
            dictionaryMap.put(description.getModelName(), new RestControllerApiDescription(description.getModelName(), descriptions));
        }
    }

    public Map<String, RestControllerApiDescription> getDictionaryMap() {
        return dictionaryMap;
    }
}
