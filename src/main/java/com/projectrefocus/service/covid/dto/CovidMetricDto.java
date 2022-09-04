package com.projectrefocus.service.covid.dto;

import java.util.Date;

public class CovidMetricDto {
    private Date date;
    private Integer value;

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
