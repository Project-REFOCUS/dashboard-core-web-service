package com.projectrefocus.service.categories.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.geography.entity.StateEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "state_cases")
public class CovidCasesEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "cases")
    private Integer cases;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCases() {
        return this.cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public CalendarDateEntity getDate() {
        return this.date;
    }

    public void setDate(CalendarDateEntity date) {
        this.date = date;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }
}