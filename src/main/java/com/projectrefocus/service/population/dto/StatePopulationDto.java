package com.projectrefocus.service.population.dto;

public class StatePopulationDto {

    private String state;
    private Integer population;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getPopulation() {
        return population;
    }
}
