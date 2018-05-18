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
    private Long reqId;

    @Column(name = "req_text")
    private String reqText;

    @Column(name = "req_prior")
    private Integer reqPrior;

    @JoinColumn(name = "req_sc_id", referencedColumnName = "sc_id")
    @ManyToOne
    private Scenario reqScenario;

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

    public Request(Long reqId, String reqText, State reqState) {
        this.reqId = reqId;
        this.reqText = reqText;
        this.reqState = reqState;
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


    public Scenario getReqScenario() {
        return reqScenario;
    }

    public void setReqScenario(Scenario reqScenario) {
        this.reqScenario = reqScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Request request = (Request) o;

        return reqId.equals(request.reqId);
    }

    @Override
    public String toString() {
        return "Request{" +
                "reqId=" + reqId +
                ", reqText='" + reqText + '\'' +
                ", reqPrior=" + reqPrior +
                ", reqScenario=" + reqScenario +
                ", reqState=" + reqState +
                ", childResponseList=" + childResponseList +
                ", parentResponseList=" + parentResponseList +
                '}';
    }
}
