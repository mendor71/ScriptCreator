package com.springapp.util;

import org.json.simple.JSONObject;
import org.springframework.hateoas.ResourceSupport;

public class JSONResponse extends ResourceSupport {
    public enum STATE {OK, ERROR, NOT_FOUND, NOT_ACCEPTABLE_DATA}

    private String state;
    private String message;

    public JSONResponse(STATE state, String message) {
        this.state = state.toString();
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
