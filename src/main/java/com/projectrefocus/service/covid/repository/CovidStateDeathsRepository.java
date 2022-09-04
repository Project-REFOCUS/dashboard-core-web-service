package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CovidStateDeathsRepository extends JpaRepository<CovidStateDeathsEntity, Integer> {

    @Query(
            "SELECT csde, se, cdate, cmonth, cday FROM CovidStateDeathsEntity csde " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csde.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csde.date.id " +
                    "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
                    "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
                    "WHERE cdate.date >= :startDate"
    )
    List<CovidStateDeathsEntity> getAllDeathsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT csde, se, cdate, cmonth, cday FROM CovidStateDeathsEntity csde " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csde.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csde.date.id " +
                    "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
                    "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
                    "WHERE cdate.date >= :startDate AND se.shortName IN (:states)"
    )
    List<CovidStateDeathsEntity> getStateDeathsOnOrAfterDate(List<String> states, Date startDate);
}
