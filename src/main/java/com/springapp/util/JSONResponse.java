package com.springapp.util;

import org.json.simple.JSONObject;
import org.springframework.hateoas.ResourceSupport;

import org.json.simple.JSONObject;

public class JSONResponse {

    public static JSONObject createOKResponse(String message) {
        JSONObject object = new JSONObject();
        object.put("state", "OK");
        object.put("message", message);
        return object;
    }

    public static JSONObject createERRResponse(String message) {
        JSONObject object = new JSONObject();
        object.put("state", "ERR");
        object.put("message", message);
        return object;
    }

    public static JSONObject createNotFoundResponse(String message) {
        JSONObject object = new JSONObject();
        object.put("state", "NOT_FOUND");
        object.put("message", message);
        return object;
    }

    public static JSONObject createNotUniqueResponse(String message) {
        JSONObject object = new JSONObject();
        object.put("state", "NOT_UNIQUE_DATA");
        object.put("message", message);
        return object;
    }

    public static JSONObject createAccessDeniedResponse(String message) {
        JSONObject object = new JSONObject();
        object.put("state", "ACCESS_DENIED");
        object.put("message", message);
        return object;
    }
}
