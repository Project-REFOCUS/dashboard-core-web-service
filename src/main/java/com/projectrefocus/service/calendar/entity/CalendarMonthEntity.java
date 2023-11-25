package com.projectrefocus.service.calendar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "calendar_month")
public class CalendarMonthEntity {

    @Id
    @Column(name = "id")
    private Byte id;

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "quarter")
    private Byte quarter;

    public void setId(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setQuarter(Byte quarter) {
        this.quarter = quarter;
    }

    public Byte getQuarter() {
        return quarter;
    }
}
