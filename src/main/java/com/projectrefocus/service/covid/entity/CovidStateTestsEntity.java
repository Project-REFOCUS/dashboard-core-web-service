package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.entity.MetricEntity;
import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.geography.entity.StateEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "state_tests")
public class CovidStateTestsEntity implements MetricEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "tests")
    private Integer tests;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTests() {
        return tests;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }

    public CalendarDateEntity getDate() {
        return date;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public StateEntity getState() {
        return state;
    }

    public Integer getValue() {
        return tests;
    }

    public CovidMetricDto toDto() {
        CovidMetricDto dto = new CovidMetricDto();
        dto.setValue(tests);
        dto.setDate(date.getDate());

        return dto;
    }
}
