package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CovidStateCasesRepository extends JpaRepository<CovidStateCasesEntity, Integer> {

    @Query(
            "SELECT csce, se, cdate, cmonth, cday FROM CovidStateCasesEntity csce " +
            "INNER JOIN FETCH StateEntity se ON se.id = csce.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csce.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate"
    )
    List<CovidStateCasesEntity> getAllCasesOnOrAfterDate(Date startDate);

    @Query(
            "SELECT CAST(SUM(csce.cases) AS integer) FROM CovidStateCasesEntity csce " +
            "INNER JOIN StateEntity se ON se.id = csce.state.id " +
            "INNER JOIN CalendarDateEntity cdate ON cdate.id = csce.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedCasesUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(SUM(csce.cases) AS integer) FROM CovidStateCasesEntity csce " +
            "INNER JOIN StateEntity se ON se.id = csce.state.id " +
            "INNER JOIN CalendarDateEntity cdate ON cdate.id = csce.date.id " +
            "WHERE se.shortName IN (:states) AND cdate.date <= :targetDate"
    )
    Integer aggregatedStateCasesUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT csce, se, cdate, cmonth, cday FROM CovidStateCasesEntity csce " +
            "INNER JOIN FETCH StateEntity se ON se.id = csce.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csce.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate AND se.shortName IN (:states)"
    )
    List<CovidStateCasesEntity> getStateCasesOnOrAfterDate(List<String> states, Date startDate);
}
