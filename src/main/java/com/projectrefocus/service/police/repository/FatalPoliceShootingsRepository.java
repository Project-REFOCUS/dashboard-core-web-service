package com.projectrefocus.service.police.repository;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface FatalPoliceShootingsRepository extends JpaRepository<CalendarDateEntity, Integer> {

    @Query(
            "SELECT cdate, fpse, ce, coe, se, ree, cmonth, cday FROM CalendarDateEntity cdate " +
            "LEFT JOIN FETCH FatalPoliceShootingsEntity fpse ON cdate.id = fpse.date.id " +
            "LEFT JOIN FETCH CityEntity ce ON fpse.city.id = ce.id " +
            "LEFT JOIN FETCH CountyEntity coe ON ce.county.id = coe.id " +
            "LEFT JOIN FETCH StateEntity se ON coe.state.id = se.id " +
            "LEFT JOIN FETCH RaceEthnicityEntity ree ON fpse.raceEthnicity.id = ree.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cdate.month.id = cmonth.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cdate.day.id = cday.id " +
            "WHERE cdate.date > :startDate"
    )
    List<CalendarDateEntity> getAllShootingsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT CAST(COALESCE(COUNT(fpse.id), 0) AS integer) FROM FatalPoliceShootingsEntity fpse " +
            "INNER JOIN CityEntity ce ON fpse.city.id = ce.id " +
            "INNER JOIN CountyEntity coe ON ce.county.id = coe.id " +
            "INNER JOIN StateEntity se ON coe.state.id = se.id " +
            "INNER JOIN CalendarDateEntity cdate ON fpse.date.id = cdate.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedShootingsUntilDate(Date targetDate);

    @Query(
            "SELECT cdate, fpse, ce, coe, se, ree, cmonth, cday FROM CalendarDateEntity cdate " +
            "LEFT JOIN FETCH FatalPoliceShootingsEntity fpse ON cdate.id = fpse.date.id " +
            "LEFT JOIN FETCH CityEntity ce ON fpse.city.id = ce.id " +
            "LEFT JOIN FETCH CountyEntity coe ON ce.county.id = coe.id " +
            "LEFT JOIN FETCH StateEntity se ON coe.state.id = se.id " +
            "LEFT JOIN FETCH RaceEthnicityEntity ree ON fpse.raceEthnicity.id = ree.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate AND (se.shortName IN (:states) OR se.shortName IS NULL)"
    )
    List<CalendarDateEntity> getStateShootingsOnOrAfterDate(List<String> states, Date startDate);

    @Query(
            "SELECT CAST(COALESCE(COUNT(fpse.id), 0) AS integer) FROM FatalPoliceShootingsEntity fpse " +
            "INNER JOIN CityEntity ce ON fpse.city.id = ce.id " +
            "INNER JOIN CountyEntity coe ON ce.county.id = coe.id " +
            "INNER JOIN StateEntity se ON coe.state.id = se.id " +
            "INNER JOIN CalendarDateEntity cdate ON fpse.date.id = cdate.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateShootingsUntilDate(List<String> states, Date targetDate);
}
