package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MultiMetricEntity;
import com.projectrefocus.service.geography.entity.StateEntity;
import com.projectrefocus.service.request.enums.SubCategory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "state_vaccinations")
public class CovidStateVaccinationsEntity implements MultiMetricEntity {

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

    public Integer getValue(SubCategory...categories) {
        return switch (categories[0]) {
            case distributed -> distributed;
            case administered -> administered;
            case administeredOneDose -> administeredOneDose;
            case administeredTwoDose -> administeredTwoDose;
            default -> null;
        };
    }

    public MetricDto toDto(SubCategory...categories) {
        MetricDto dto = new MetricDto();
        dto.setDate(date.getDate());
        dto.setValue(getValue(categories));
        return dto;
    }
}
