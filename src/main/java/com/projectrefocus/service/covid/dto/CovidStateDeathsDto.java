package com.projectrefocus.service.covid.dto;

import java.util.Date;

public class CovidStateDeathsDto extends CovidStateMetricDto {
    private Integer deaths;

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getDeaths() {
        return deaths;
    }
}
