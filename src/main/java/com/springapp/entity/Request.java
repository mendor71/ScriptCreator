package com.springapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "public.requests")
public class Request extends ResourceSupport {
    @Id
    @Column(name = "req_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reqId;
    @Column(name = "req_text")
    private String reqText;
    @Column(name = "req_prior")
    private Integer reqPrior;
    @JoinColumn(name = "req_cat_id", referencedColumnName = "cat_id")
    @ManyToOne
    private Category reqCategory;
    @JoinColumn(name = "req_state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State reqState;
    @JoinTable(name = "requests_to_responses", joinColumns = {@JoinColumn(name = "req_to_resp_request_id")}, inverseJoinColumns = {@JoinColumn(name = "req_to_resp_response_id")})
    @ManyToMany
    private List<Response> childResponseList = new ArrayList<Response>();

    @JsonIgnore
    @JoinTable(name = "responses_to_requests", joinColumns = {@JoinColumn(name = "resp_to_req_request_id")}, inverseJoinColumns = {@JoinColumn(name = "resp_to_req_response_id")})
    @ManyToMany
    private List<Response> parentResponseList = new ArrayList<Response>();

    public Request() {
    }

    public Request(Integer reqId, String reqText, Category reqCategory, State reqState) {
        this.reqId = reqId;
        this.reqText = reqText;
        this.reqCategory = reqCategory;
        this.reqState = reqState;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
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

    public List<Response> getChildResponseList() {
        return childResponseList;
    }

    public void setChildResponseList(List<Response> childResponseList) {
        this.childResponseList = childResponseList;
    }

    public List<Response> getParentResponseList() {
        return parentResponseList;
    }

    public void setParentResponseList(List<Response> parentResponseList) {
        this.parentResponseList = parentResponseList;
    }

    public Integer getReqPrior() {
        return reqPrior;
    }

    public void setReqPrior(Integer reqPrior) {
        this.reqPrior = reqPrior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Request request = (Request) o;

        return reqId.equals(request.reqId);
    }
}
