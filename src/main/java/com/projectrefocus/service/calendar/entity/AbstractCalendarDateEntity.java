package com.projectrefocus.service.calendar.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.sql.Date;

@MappedSuperclass
@Table(name = "calendar_date")
public abstract class AbstractCalendarDateEntity {

    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "date")
    private Date date;

    @Column(name = "week_number")
    private Byte weekNumber;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_month_id")
    private CalendarMonthEntity month;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_day_id")
    private CalendarDayEntity day;

    public void setId(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setWeekNumber(Byte weekNumber) {
        this.weekNumber = weekNumber;
    }

    public Byte getWeekNumber() {
        return weekNumber;
    }

    public void setMonth(CalendarMonthEntity month) {
        this.month = month;
    }

    public CalendarMonthEntity getMonth() {
        return month;
    }

    public void setDay(CalendarDayEntity day) {
        this.day = day;
    }

    public CalendarDayEntity getDay() {
        return day;
    }
}
