package com.projectrefocus.service.calendar.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "calendar_date")
public class CalendarDateEntity {

    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "date")
    private Date date;

    @Column(name = "week_number")
    private Byte weekNumber;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_month_id")
    private CalendarMonthEntity month;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.EAGER)
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

    public void setDay(CalendarDayEntity day) {
        this.day = day;
    }

    public CalendarDayEntity getDay() {
        return day;
    }

    public void setMonth(CalendarMonthEntity month) {
        this.month = month;
    }

    public CalendarMonthEntity getMonth() {
        return month;
    }
}
