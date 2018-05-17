package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

@Entity
@Table(name = "public.scenarios")
public class Scenario extends ResourceSupport {
    @Id
    @Column(name = "sc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scId;

    @JoinColumn(name = "sc_cat_id", referencedColumnName = "cat_id")
    @ManyToOne
    private Category scCatId;

    @Column(name = "sc_name")
    private String scName;

    @JoinColumn(name = "sc_state_id", referencedColumnName = "state_id")
    @ManyToOne
    private State scStateId;

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public Category getScCatId() {
        return scCatId;
    }

    public void setScCatId(Category scCatId) {
        this.scCatId = scCatId;
    }

    public State getScStateId() {
        return scStateId;
    }

    public void setScStateId(State scStateId) {
        this.scStateId = scStateId;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Scenario scenario = (Scenario) o;

        return scId != null ? scId.equals(scenario.scId) : scenario.scId == null;
    }
}
