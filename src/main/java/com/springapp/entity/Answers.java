package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class Answers extends ResourceSupport {
    private Long answId;
    private String answText;
    private Category answCategory;
    private State answState;
    private List<Requests> requestsList;

    public Answers() {
    }

    public Answers(Long answId, String answText, Category answCategory, State answState, List<Requests> requestsList) {
        this.answId = answId;
        this.answText = answText;
        this.answCategory = answCategory;
        this.answState = answState;
        this.requestsList = requestsList;
    }

    public Long getAnswId() {
        return answId;
    }

    public void setAnswId(Long answId) {
        this.answId = answId;
    }

    public String getAnswText() {
        return answText;
    }

    public void setAnswText(String answText) {
        this.answText = answText;
    }

    public Category getAnswCategory() {
        return answCategory;
    }

    public void setAnswCategory(Category answCategory) {
        this.answCategory = answCategory;
    }

    public State getAnswState() {
        return answState;
    }

    public void setAnswState(State answState) {
        this.answState = answState;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }
}
