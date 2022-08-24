package com.projectrefocus.service.covid.dto;

public class CovidStateTestsDto extends CovidStateMetricDto {
    private Integer tests;

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTests() {
        return tests;
    }
}
