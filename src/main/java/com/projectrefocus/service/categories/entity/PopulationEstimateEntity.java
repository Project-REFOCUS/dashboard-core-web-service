package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.geography.entity.StateEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "state_population_2020")
public class PopulationEstimateEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "population")
    private Integer population;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }
}