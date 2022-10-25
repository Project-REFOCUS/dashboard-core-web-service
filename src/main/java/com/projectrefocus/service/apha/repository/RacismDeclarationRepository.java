package com.projectrefocus.service.apha.repository;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RacismDeclarationRepository extends JpaRepository<CalendarDateEntity, Integer> {

    @Query(
          "SELECT cdate, rde, ce, coe, se, cmonth, cday FROM CalendarDateEntity cdate " +
          "LEFT JOIN FETCH RacismDeclarationEntity rde ON cdate.id = rde.date.id " +
          "LEFT JOIN FETCH CityEntity ce ON rde.city.id = ce.id " +
          "LEFT JOIN FETCH CountyEntity coe ON ce.county.id = coe.id " +
          "LEFT JOIN FETCH StateEntity se ON coe.state.id = se.id " +
          "INNER JOIN FETCH CalendarMonthEntity cmonth ON cdate.month.id = cmonth.id " +
          "INNER JOIN FETCH CalendarDayEntity cday ON cdate.day.id = cday.id " +
          "WHERE cdate.date > :startDate"
    )
    List<CalendarDateEntity> getAllDeclarationsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT CAST(COALESCE(COUNT(rde.id), 0) AS integer) FROM RacismDeclarationEntity rde " +
            "INNER JOIN CityEntity ce ON rde.city.id = ce.id " +
            "INNER JOIN CountyEntity coe ON ce.county.id = coe.id " +
            "INNER JOIN StateEntity se ON coe.state.id = se.id " +
            "INNER JOIN CalendarDateEntity cdate ON rde.date.id = cdate.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedDeclarationsUntilDate(Date targetDate);


    @Query(
            "SELECT cdate, rde, ce, coe, se, cmonth, cday FROM CalendarDateEntity cdate " +
            "LEFT JOIN FETCH RacismDeclarationEntity rde ON cdate.id = rde.date.id " +
            "LEFT JOIN FETCH CityEntity ce ON rde.city.id = ce.id " +
            "LEFT JOIN FETCH CountyEntity coe ON ce.county.id = coe.id " +
            "LEFT JOIN FETCH StateEntity se ON coe.state.id = se.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cdate.month.id = cmonth.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cdate.day.id = cday.id " +
            "WHERE cdate.date > :startDate AND (se.shortName IN (:states) OR se.shortName IS NULL)"
    )
    List<CalendarDateEntity> getStateDeclarationsOnOrAfterDate(List<String> states, Date startDate);

    @Query(
            "SELECT CAST(COALESCE(COUNT(rde.id), 0) AS integer) FROM RacismDeclarationEntity rde " +
            "INNER JOIN CityEntity ce ON rde.city.id = ce.id " +
            "INNER JOIN CountyEntity coe ON ce.county.id = coe.id " +
            "INNER JOIN StateEntity se ON coe.state.id = se.id " +
            "INNER JOIN CalendarDateEntity cdate ON rde.date.id = cdate.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateDeclarationsUntilDate(List<String> states, Date targetDate);
}
