package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "public.states")
public class State extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "state_name")
    private String stateName;
    @Column(name = "state_localized_name")
    private String stateLocalizedName;

    public State() {
    }

    public State(Integer stateId, String stateName, String stateNameLocale) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateLocalizedName = stateNameLocale;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateLocalizedName() {
        return stateLocalizedName;
    }

    public void setStateLocalizedName(String stateLocalizedName) {
        this.stateLocalizedName = stateLocalizedName;
    }
}
