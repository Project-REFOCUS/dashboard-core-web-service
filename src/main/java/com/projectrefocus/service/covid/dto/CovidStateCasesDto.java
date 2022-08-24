package com.projectrefocus.service.covid.dto;

import java.util.Date;

public class CovidStateCasesDto extends CovidStateMetricDto {
    private Integer cases;

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getCases() {
        return cases;
    }
}
