package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MetricEntity;
import com.projectrefocus.service.geography.entity.StateEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "WeeklyDeaths", classes = {
                @ConstructorResult(
                        targetClass = CovidStateDeathsEntity.class,
                        columns = {
                                @ColumnResult(name = "date", type = Date.class),
                                @ColumnResult(name = "deaths", type = Integer.class)
                        }
                )
        })
})
@NamedNativeQuery(
        name = "allWeeklyDeaths",
        query = "SELECT MIN(cdate.date) as date, SUM(csde.deaths) as deaths, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_deaths csde, calendar_date cdate WHERE cdate.id = csde.calendar_date_id AND cdate.date > :startDate GROUP BY calendar_week",
        resultSetMapping = "WeeklyDeaths"
)
@NamedNativeQuery(
        name = "stateWeeklyDeaths",
        query = "SELECT MIN(cdate.date) as date, SUM(csde.deaths) as deaths, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_deaths csde, state s,  calendar_date cdate WHERE cdate.id = csde.calendar_date_id AND csde.state_id = s.id AND cdate.date > :startDate " +
                "AND s.short_name IN (:states) GROUP BY calendar_week",
        resultSetMapping = "WeeklyDeaths"
)
@Entity
@Table(name = "state_deaths")
public class CovidStateDeathsEntity implements MetricEntity {

    public CovidStateDeathsEntity() {}

    public CovidStateDeathsEntity(Date date, Integer deaths) {
        CalendarDateEntity calendarDate = new CalendarDateEntity();
        calendarDate.setDate(new java.sql.Date(date.getTime()));
        this.date = calendarDate;
        this.deaths = deaths;
    }

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "deaths")
    private Integer deaths;

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

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getDeaths() {
        return deaths;
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

    public Integer getValue() {
        return deaths;
    }

    public MetricDto toDto() {
        MetricDto dto = new MetricDto();
        dto.setValue(deaths);
        dto.setDate(date.getDate());

        return dto;
    }
}
