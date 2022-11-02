package com.projectrefocus.service.osha.repository;

import com.projectrefocus.service.osha.entity.OshaComplaintsCalendarDateEntity;
import com.projectrefocus.service.osha.entity.OshaComplaintsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OshaComplaintsRepository extends JpaRepository<OshaComplaintsEntity, Integer> {

    @Query(
            "SELECT CAST(COALESCE(COUNT(oce.id), 0) AS integer) FROM OshaComplaintsEntity oce " +
            "INNER JOIN FETCH CityEntity ce ON ce.id = oce.city.id " +
            "INNER JOIN FETCH CountyEntity coe ON coe.id = ce.county.id " +
            "INNER JOIN FETCH StateEntity se ON se.id = coe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = oce.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedComplaintsUntilDate(Date targetDate);

    @Query(
            "SELECT occde, oce, ce, coe, se, cdate, cmonth, cday FROM OshaComplaintsCalendarDateEntity occde " +
            "LEFT JOIN FETCH OshaComplaintsEntity oce ON oce.date.id=occde.id " +
            "LEFT JOIN FETCH CityEntity ce ON ce.id = oce.city.id " +
            "LEFT JOIN FETCH CountyEntity coe ON coe.id = ce.county.id " +
            "LEFT JOIN FETCH StateEntity se ON se.id = coe.state.id " +
            "LEFT JOIN FETCH CalendarDateEntity cdate ON cdate.id = oce.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = occde.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = occde.day.id " +
            "WHERE occde.date > :startDate"
    )
    List<OshaComplaintsCalendarDateEntity> getAllComplaintsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT CAST(COALESCE(COUNT(oce.id), 0) AS integer) FROM OshaComplaintsEntity oce " +
            "INNER JOIN FETCH CityEntity ce ON ce.id = oce.city.id " +
            "INNER JOIN FETCH CountyEntity coe ON coe.id = ce.county.id " +
            "INNER JOIN FETCH StateEntity se ON se.id = coe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = oce.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedComplaintsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT occde, oce, ce, coe, se, cdate, cmonth, cday FROM OshaComplaintsCalendarDateEntity occde " +
            "LEFT JOIN FETCH OshaComplaintsEntity oce ON oce.date.id=occde.id " +
            "LEFT JOIN FETCH CityEntity ce ON ce.id = oce.city.id " +
            "LEFT JOIN FETCH CountyEntity coe ON coe.id = ce.id " +
            "LEFT JOIN FETCH StateEntity se ON se.id = coe.state.id " +
            "LEFT JOIN FETCH CalendarDateEntity cdate ON cdate.id = oce.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = occde.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = occde.day.id " +
            "WHERE occde.date > :startDate AND (se.shortName IN (:states) OR se.shortName IS NULL)"
    )
    List<OshaComplaintsCalendarDateEntity> getStateComplaintsOnOrAfterDate(List<String> states, Date startDate);
}