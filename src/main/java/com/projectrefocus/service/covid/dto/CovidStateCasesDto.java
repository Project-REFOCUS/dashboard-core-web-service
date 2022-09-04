package com.projectrefocus.service.covid.dto;

public class CovidStateCasesDto extends CovidStateMetricDto {
    private Long cases;

    public void setCases(Long cases) {
        this.cases = cases;
    }

    public Long getCases() {
        return cases;
    }
}
