package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

public class State extends ResourceSupport {
    private Long stateId;
    private String stateName;
    private String stateNameLocale;

    public State() {
    }

    public State(Long stateId, String stateName, String stateNameLocale) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateNameLocale = stateNameLocale;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateNameLocale() {
        return stateNameLocale;
    }

    public void setStateNameLocale(String stateNameLocale) {
        this.stateNameLocale = stateNameLocale;
    }
}
