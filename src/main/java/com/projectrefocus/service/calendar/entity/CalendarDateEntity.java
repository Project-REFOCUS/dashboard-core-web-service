package com.projectrefocus.service.calendar.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "calendar_date")
public class CalendarDateEntity implements Comparable<CalendarDateEntity> {

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
    @OneToOne(fetch = FetchType.LAZY)
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

    public int compareTo(CalendarDateEntity entity) {
        if (entity == null || entity.getDate() == null) {
            return 1;
        }
        if (this.getDate() == null) {
            return -1;
        }
        return this.getDate().compareTo(entity.getDate());
    }
}
