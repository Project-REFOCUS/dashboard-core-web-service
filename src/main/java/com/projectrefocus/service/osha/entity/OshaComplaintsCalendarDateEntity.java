package com.projectrefocus.service.osha.entity;

import com.projectrefocus.service.calendar.entity.AbstractCalendarDateEntity;
import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "calendar_date")
public class OshaComplaintsCalendarDateEntity extends AbstractCalendarDateEntity {

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_date_id", referencedColumnName = "id")
    private List<OshaComplaintsEntity> complaints;

    public void setComplaints(List<OshaComplaintsEntity> complaints) {
        this.complaints = complaints;
    }

    public List<OshaComplaintsEntity> getComplaints() {
        return complaints;
    }
}
