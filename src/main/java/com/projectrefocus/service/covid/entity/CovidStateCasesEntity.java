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
        @SqlResultSetMapping(name = "GroupedCases", classes = {
                @ConstructorResult(
                        targetClass = CovidStateCasesEntity.class,
                        columns = {
                                @ColumnResult(name = "date", type = Date.class),
                                @ColumnResult(name = "cases", type = Integer.class)
                        }
                )
        })
})
@NamedNativeQuery(
        name = "allWeeklyCases",
        query = "SELECT MIN(cdate.date) as date, SUM(csce.cases) as cases, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_cases csce, calendar_date cdate WHERE cdate.id = csce.calendar_date_id AND cdate.date > :startDate GROUP BY calendar_week",
        resultSetMapping = "GroupedCases"
)
@NamedNativeQuery(
        name = "stateWeeklyCases",
        query = "SELECT MIN(cdate.date) as date, SUM(csce.cases) as cases, CONCAT_WS('_', cdate.week_number, DATE_FORMAT(cdate.date, '%Y')) as calendar_week " +
                "FROM state_cases csce, state s, calendar_date cdate WHERE cdate.id = csce.calendar_date_id AND csce.state_id = s.id AND cdate.date > :startDate " +
                "AND s.short_name IN (:states) GROUP BY calendar_week",
        resultSetMapping = "GroupedCases"
)
@NamedNativeQuery(
        name = "allMonthlyCases",
        query = "SELECT MIN(cdate.date) as date, SUM(csce.cases) as cases, CONCAT_WS('_', cmonth.short_name, DATE_FORMAT(cdate.date, '%Y')) as month_by_year " +
                "FROM state_cases csce, calendar_date cdate, calendar_month cmonth " +
                "WHERE cdate.id = csce.calendar_date_id AND cmonth.id = cdate.calendar_month_id AND cdate.date > :startDate " +
                "GROUP BY month_by_year ORDER BY date",
        resultSetMapping = "GroupedCases"
)
@NamedNativeQuery(
        name = "stateMonthlyCases",
        query = "SELECT MIN(cdate.date) as date, SUM(csce.cases) as cases, CONCAT_WS('_', cmonth.short_name, DATE_FORMAT(cdate.date, '%Y')) as month_by_year " +
                "FROM state_cases csce, state s, calendar_date cdate, calendar_month cmonth " +
                "WHERE cdate.id = csce.calendar_date_id AND cmonth.id = cdate.calendar_month_id AND s.id = csce.state_id AND cdate.date > :startDate AND s.short_name IN (:states) " +
                "GROUP BY month_by_year ORDER BY date",
        resultSetMapping = "GroupedCases"
)
@Entity
@Table(name = "state_cases")
public class CovidStateCasesEntity implements MetricEntity {

    public CovidStateCasesEntity() {}

    public CovidStateCasesEntity(Date date, Integer cases) {
        CalendarDateEntity calendarDate = new CalendarDateEntity();
        calendarDate.setDate(new java.sql.Date(date.getTime()));
        this.date = calendarDate;
        this.cases = cases;
    }

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cases")
    private Integer cases;

    @Fetch(FetchMode.JOIN)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_date_id")
    private CalendarDateEntity date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private StateEntity state;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getCases() {
        return cases;
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
        return cases;
    }

    public MetricDto toDto() {
        MetricDto dto = new MetricDto();
        dto.setDate(date.getDate());
        dto.setValue(cases);

        return dto;
    }
}
