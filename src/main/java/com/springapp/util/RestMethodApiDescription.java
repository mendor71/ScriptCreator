package com.springapp.util;

public class RestMethodApiDescription {
    private String url;
    private String modelName;
    private String params;
    private String method;
    private String actionName;

    public RestMethodApiDescription(String url, String method, String actionName, String modelName, String params) {
        this.url = url;
        this.modelName = modelName;
        this.params = params;
        this.method = method;
        this.actionName = actionName;
    }

    public String getUrl() {
        return url;
    }

    public String getModelName() {
        return modelName;
    }

    public String getParams() {
        return params;
    }

    public String getMethod() {
        return method;
    }

    public String getActionName() {
        return actionName;
    }
}
