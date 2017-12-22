package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class Requests extends ResourceSupport {
    private Long reqId;
    private String reqText;
    private Category reqCategory;
    private State reqState;
    private List<Answers> answersList;

    public Requests() {
    }

    public Requests(Long reqId, String reqText, Category reqCategory, State reqState, List<Answers> answersList) {
        this.reqId = reqId;
        this.reqText = reqText;
        this.reqCategory = reqCategory;
        this.reqState = reqState;
        this.answersList = answersList;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public String getReqText() {
        return reqText;
    }

    public void setReqText(String reqText) {
        this.reqText = reqText;
    }

    public Category getReqCategory() {
        return reqCategory;
    }

    public void setReqCategory(Category reqCategory) {
        this.reqCategory = reqCategory;
    }

    public State getReqState() {
        return reqState;
    }

    public void setReqState(State reqState) {
        this.reqState = reqState;
    }

    public List<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }
}
