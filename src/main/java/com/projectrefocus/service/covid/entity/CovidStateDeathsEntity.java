package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateMetricDto;
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
@Table(name = "state_deaths")
public class CovidStateDeathsEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "deaths")
    private Integer deaths;

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

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getDeaths() {
        return deaths;
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

    public CovidStateMetricDto toDto() {
        CovidStateMetricDto dto = new CovidStateMetricDto();
        dto.setId(id);
        dto.setValue(deaths);
        dto.setDate(date.getDate());
        dto.setState(state.getName());

        return dto;
    }
}
