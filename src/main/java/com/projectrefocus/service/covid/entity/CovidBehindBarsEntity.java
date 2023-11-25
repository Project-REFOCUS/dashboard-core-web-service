package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MultiMetricEntity;
import com.projectrefocus.service.geography.entity.StateEntity;
import com.projectrefocus.service.request.enums.SubCategory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;

@Entity
@Table(name = "covid_behind_bars")
public class CovidBehindBarsEntity implements MultiMetricEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "resident_cases")
    private Integer residentCases;

    @Column(name = "staff_cases")
    private Integer staffCases;

    @Column(name = "resident_deaths")
    private Integer residentDeaths;

    @Column(name = "staff_deaths")
    private Integer staffDeaths;

    @Column(name = "resident_tests")
    private Integer residentTests;

    @Column(name = "resident_administered_one_dose")
    private Integer residentAdministeredOneDose;

    @Column(name = "staff_administered_one_dose")
    private Integer staffAdministeredOneDose;

    @Column(name = "resident_administered_two_dose")
    private Integer residentAdministeredTwoDose;

    @Column(name = "staff_administered_two_dose")
    private Integer staffAdministeredTwoDose;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setResidentCases(Integer residentCases) {
        this.residentCases = residentCases;
    }

    public Integer getResidentCases() {
        return residentCases;
    }

    public void setStaffCases(Integer staffCases) {
        this.staffCases = staffCases;
    }

    public Integer getStaffCases() {
        return staffCases;
    }

    public void setResidentDeaths(Integer residentDeaths) {
        this.residentDeaths = residentDeaths;
    }

    public Integer getResidentDeaths() {
        return residentDeaths;
    }

    public void setStaffDeaths(Integer staffDeaths) {
        this.staffDeaths = staffDeaths;
    }

    public Integer getStaffDeaths() {
        return staffDeaths;
    }

    public void setResidentTests(Integer residentTests) {
        this.residentTests = residentTests;
    }

    public Integer getResidentTests() {
        return residentTests;
    }

    public void setResidentAdministeredOneDose(Integer residentAdministeredOneDose) {
        this.residentAdministeredOneDose = residentAdministeredOneDose;
    }

    public Integer getResidentAdministeredOneDose() {
        return residentAdministeredOneDose;
    }

    public void setStaffAdministeredOneDose(Integer staffAdministeredOneDose) {
        this.staffAdministeredOneDose = staffAdministeredOneDose;
    }

    public Integer getStaffAdministeredOneDose() {
        return staffAdministeredOneDose;
    }

    public void setResidentAdministeredTwoDose(Integer residentAdministeredTwoDose) {
        this.residentAdministeredTwoDose = residentAdministeredTwoDose;
    }

    public Integer getResidentAdministeredTwoDose() {
        return residentAdministeredTwoDose;
    }

    public void setStaffAdministeredTwoDose(Integer staffAdministeredTwoDose) {
        this.staffAdministeredTwoDose = staffAdministeredTwoDose;
    }

    public Integer getStaffAdministeredTwoDose() {
        return staffAdministeredTwoDose;
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

    public Integer getValue(SubCategory ...categories) {
        if (categories.length == 2) {
            switch (categories[0]) {
                case residents:
                    switch (categories[1]) {
                        case cases -> { return residentCases; }
                        case deaths -> { return residentDeaths; }
                        case tests -> { return residentTests; }
                        case administeredOneDose -> { return residentAdministeredOneDose; }
                        case administeredTwoDose -> { return residentAdministeredTwoDose; }
                    }
                case staff:
                    switch (categories[1]) {
                        case cases -> { return staffCases; }
                        case deaths -> { return staffDeaths; }
                        case administeredOneDose -> { return staffAdministeredOneDose; }
                        case administeredTwoDose -> { return staffAdministeredTwoDose; }
                    }
            }
        }
        return null;
    }

    public MetricDto toDto(SubCategory ...categories) {
        MetricDto dto = new MetricDto();
        dto.setValue(getValue(categories));
        dto.setDate(date.getDate());
        return dto;
    }
}
