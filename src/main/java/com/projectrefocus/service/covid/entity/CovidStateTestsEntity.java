package com.projectrefocus.service.covid.entity;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.common.entity.MetricEntity;
import com.projectrefocus.service.geography.entity.StateEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import java.util.Date;

@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "GroupedTests", classes = {
                @ConstructorResult(
                        targetClass = CovidStateTestsEntity.class,
                        columns = {
                                @ColumnResult(name = "date", type = Date.class),
                                @ColumnResult(name = "tests", type = Integer.class)
                        }
                )
        })
})
@NamedNativeQuery(
        name = "allWeeklyTests",
        query = "SELECT MIN(cdate.date) as date, SUM(cste.tests) as tests, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_tests cste, calendar_date cdate WHERE cdate.id = cste.calendar_date_id AND cdate.date > :startDate GROUP BY calendar_week",
        resultSetMapping = "GroupedTests"
)
@NamedNativeQuery(
        name = "stateWeeklyTests",
        query = "SELECT MIN(cdate.date) as date, SUM(cste.tests) as tests, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_tests cste, state s, calendar_date cdate WHERE cdate.id = cste.calendar_date_id AND cste.state_id = s.id AND cdate.date > :startDate " +
                "AND s.short_name IN (:states) GROUP BY calendar_week",
        resultSetMapping = "GroupedTests"
)
@NamedNativeQuery(
        name = "allMonthlyTests",
        query = "SELECT MIN(cdate.date) as date, SUM(cste.tests) as tests, CONCAT_WS('_', cmonth.short_name, DATE_FORMAT(cdate.date, '%Y')) as month_by_year " +
                "FROM state_tests cste, calendar_date cdate, calendar_month cmonth " +
                "WHERE cdate.id = cste.calendar_date_id AND cmonth.id = cdate.calendar_month_id AND cdate.date > :startDate " +
                "GROUP BY month_by_year ORDER BY date",
        resultSetMapping = "GroupedTests"
)
@NamedNativeQuery(
        name = "stateMonthlyTests",
        query = "SELECT MIN(cdate.date) as date, SUM(cste.tests) as tests, CONCAT_WS('_', cmonth.short_name, DATE_FORMAT(cdate.date, '%Y')) as month_by_year " +
                "FROM state_tests cste, state s, calendar_date cdate, calendar_month cmonth " +
                "WHERE cdate.id = cste.calendar_date_id AND cmonth.id = cdate.calendar_month_id AND s.id = cste.state_id AND cdate.date > :startDate AND s.short_name IN (:states) " +
                "GROUP BY month_by_year ORDER BY date",
        resultSetMapping = "GroupedTests"
)
@Entity
@Table(name = "state_tests")
public class CovidStateTestsEntity implements MetricEntity {

    public CovidStateTestsEntity() {}

    public CovidStateTestsEntity(Date date, Integer tests) {
        CalendarDateEntity calendarDate = new CalendarDateEntity();
        calendarDate.setDate(new java.sql.Date(date.getTime()));
        this.date = calendarDate;
        this.tests = tests;
    }

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "tests")
    private Integer tests;

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

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTests() {
        return tests;
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
        return tests;
    }

    public MetricDto toDto() {
        MetricDto dto = new MetricDto();
        dto.setValue(tests);
        dto.setDate(date.getDate());

        return dto;
    }
}
