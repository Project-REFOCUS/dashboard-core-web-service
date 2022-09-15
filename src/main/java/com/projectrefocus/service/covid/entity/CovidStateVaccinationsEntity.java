package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
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
@Table(name = "state_vaccinations")
public class CovidStateVaccinationsEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "distributed")
    private Integer distributed;

    @Column(name = "administered")
    private Integer administered;

    @Column(name = "administered_one_dose")
    private Integer administeredOneDose;

    @Column(name = "administered_two_dose")
    private Integer administeredTwoDose;

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

    public void setAdministered(Integer administered) {
        this.administered = administered;
    }

    public Integer getAdministered() {
        return administered;
    }

    public void setDistributed(Integer distributed) {
        this.distributed = distributed;
    }

    public Integer getDistributed() {
        return distributed;
    }

    public void setAdministeredOneDose(Integer administeredOneDose) {
        this.administeredOneDose = administeredOneDose;
    }

    public Integer getAdministeredOneDose() {
        return administeredOneDose;
    }

    public void setAdministeredTwoDose(Integer administeredTwoDose) {
        this.administeredTwoDose = administeredTwoDose;
    }

    public Integer getAdministeredTwoDose() {
        return administeredTwoDose;
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
}
