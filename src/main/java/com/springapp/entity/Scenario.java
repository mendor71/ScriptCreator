package com.springapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "public.scenarios")
public class Scenario {
    @Id
    @Column(name = "sc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scId;

    @JoinColumn(name = "sc_cat_id", referencedColumnName = "cat_id")
    @ManyToOne
    private Category scCatId;

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
}
