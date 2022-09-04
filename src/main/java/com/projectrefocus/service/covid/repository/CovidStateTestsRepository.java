package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CovidStateTestsRepository extends JpaRepository<CovidStateTestsEntity, Integer> {

    @Query(
            "SELECT CAST(SUM(cste.tests) AS integer) FROM CovidStateTestsEntity cste " +
                    "INNER JOIN StateEntity se ON se.id = cste.state.id " +
                    "INNER JOIN CalendarDateEntity cdate ON cdate.id = cste.date.id " +
                    "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedDeathsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(SUM(cste.tests) AS integer) FROM CovidStateTestsEntity cste " +
                    "INNER JOIN StateEntity se ON se.id = cste.state.id " +
                    "INNER JOIN CalendarDateEntity cdate ON cdate.id = cste.date.id " +
                    "WHERE se.shortName IN (:states) AND cdate.date <= :targetDate"
    )
    Integer aggregatedStateDeathsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT cste, se, cdate, cmonth, cday FROM CovidStateTestsEntity cste " +
                    "INNER JOIN FETCH StateEntity se ON se.id = cste.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cste.date.id " +
                    "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
                    "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
                    "WHERE cdate.date > :startDate"
    )
    List<CovidStateTestsEntity> getAllTestsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT cste, se, cdate, cmonth, cday FROM CovidStateTestsEntity cste " +
                    "INNER JOIN FETCH StateEntity se ON se.id = cste.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cste.date.id " +
                    "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
                    "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
                    "WHERE cdate.date > :startDate AND se.shortName IN (:states)"
    )
    List<CovidStateTestsEntity> getStateCasesOnOrAfterDate(List<String> states, Date startDate);
}
