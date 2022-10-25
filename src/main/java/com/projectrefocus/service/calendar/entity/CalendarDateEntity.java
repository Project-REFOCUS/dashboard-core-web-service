package com.projectrefocus.service.calendar.entity;

import com.projectrefocus.service.apha.entity.RacismDeclarationEntity;
import com.projectrefocus.service.police.entity.FatalPoliceShootingsEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_month_id")
    private CalendarMonthEntity month;

    @Fetch(FetchMode.JOIN)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_day_id")
    private CalendarDayEntity day;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id", referencedColumnName = "id")
    private List<FatalPoliceShootingsEntity> shootings;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_date_id", referencedColumnName = "id")
    private List<RacismDeclarationEntity> declarations;

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

    public void setShootings(List<FatalPoliceShootingsEntity> shootings) {
        this.shootings = shootings;
    }

    public List<FatalPoliceShootingsEntity> getShootings() {
        return this.shootings;
    }

    public void setDeclarations(List<RacismDeclarationEntity> declarations) {
        this.declarations = declarations;
    }

    public List<RacismDeclarationEntity> getDeclarations() {
        return declarations;
    }
}
