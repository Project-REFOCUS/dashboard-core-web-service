package com.projectrefocus.service.covid.dto;

import java.util.Date;

public abstract class CovidStateMetricDto {

    private Integer id;
    private Date date;
    private String state;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
