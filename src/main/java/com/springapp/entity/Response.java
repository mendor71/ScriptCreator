package com.springapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "public.responses")
public class Response extends ResourceSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resp_id")
    private Integer respId;
    @Column(name = "resp_text")
    private String respText;
    @Column(name = "resp_prior")
    private Integer respPrior;
    @JoinColumn(name = "resp_cat_id", referencedColumnName = "cat_id")
    @ManyToOne
    private Category respCategory;
    @JoinColumn(name = "resp_state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State respState;
    @JsonIgnore
    @ManyToMany(mappedBy = "childResponseList")
    private List<Request> parentRequestList = new ArrayList<Request>();
    @ManyToMany(mappedBy = "parentResponseList")
    private List<Request> childRequestList = new ArrayList<Request>();

    public Response() {
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    public Integer getRespPrior() {
        return respPrior;
    }

    public void setRespPrior(Integer respPrior) {
        this.respPrior = respPrior;
    }

    public Category getRespCategory() {
        return respCategory;
    }

    public void setRespCategory(Category respCategory) {
        this.respCategory = respCategory;
    }

    public State getRespState() {
        return respState;
    }

    public void setRespState(State respState) {
        this.respState = respState;
    }

    public List<Request> getParentRequestList() {
        return parentRequestList;
    }

    public void setParentRequestList(List<Request> parentRequestList) {
        this.parentRequestList = parentRequestList;
    }

    public List<Request> getChildRequestList() {
        return childRequestList;
    }

    public void setChildRequestList(List<Request> childRequestList) {
        this.childRequestList = childRequestList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Response response = (Response) o;

        return respId != null ? respId.equals(response.respId) : response.respId == null;
    }
}
