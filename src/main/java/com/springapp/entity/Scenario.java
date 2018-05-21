package com.springapp.entity;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @JoinColumn(name = "sc_owner_user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User scOwnerUserId;

    @JoinTable(name = "user_scenarios_access", joinColumns = {@JoinColumn(name = "usa_sc_id")}, inverseJoinColumns = {@JoinColumn(name = "usa_user_id")})
    @ManyToMany
    private List<User> scAccessUsers = new ArrayList<>();

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

    public User getScOwnerUserId() {
        return scOwnerUserId;
    }

    public void setScOwnerUserId(User scOwnerUserId) {
        this.scOwnerUserId = scOwnerUserId;
    }

    public List<User> getScAccessUsers() {
        return scAccessUsers;
    }

    public void setScAccessUsers(List<User> scAccessUsers) {
        this.scAccessUsers = scAccessUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Scenario scenario = (Scenario) o;

        return scId != null ? scId.equals(scenario.scId) : scenario.scId == null;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "scId=" + scId +
                ", scCatId=" + scCatId +
                ", scName='" + scName + '\'' +
                ", scStateId=" + scStateId +
                ", scOwnerUserId=" + scOwnerUserId +
                ", scAccessUsers=" + scAccessUsers +
                '}';
    }
}
