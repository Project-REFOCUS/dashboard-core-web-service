package com.projectrefocus.service.population.entity;

import com.projectrefocus.service.geography.entity.StateEntity;
import com.projectrefocus.service.population.dto.StatePopulationDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state_population_2020")
public class StatePopulationEntity {

    @Id
    @Column(name = "id")
    private Byte id;

    @Column(name = "population")
    private Integer population;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public void setId(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public StateEntity getState() {
        return state;
    }

    public StatePopulationDto toDto() {
        StatePopulationDto dto = new StatePopulationDto();
        dto.setPopulation(population);
        dto.setState(state.getName());

        return dto;
    }
}
