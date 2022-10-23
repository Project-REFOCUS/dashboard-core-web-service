package com.projectrefocus.service.common.dto;

import java.util.Date;

public class MetricDto {

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
