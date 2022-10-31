package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidBehindBarsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CovidBehindBarsRepository extends JpaRepository<CovidBehindBarsEntity, Integer> {
    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentCases), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedResidentCasesUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentCases), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateResidentCasesUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffCases), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedStaffCasesUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffCases), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateStaffCasesUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentDeaths), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedResidentDeathsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentDeaths), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateResidentDeathsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffDeaths), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedStaffDeathsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffDeaths), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateStaffDeathsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentTests), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedResidentTestsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentTests), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateResidentTestsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredOneDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedResidentAdministeredOneDoseUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredOneDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateResidentAdministeredOneDoseUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffAdministeredOneDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedStaffAdministeredOneDoseUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.staffAdministeredOneDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateStaffAdministeredOneDoseUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredTwoDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedResidentAdministeredTwoDoseUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredTwoDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateResidentAdministeredTwoDoseUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredTwoDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedStaffAdministeredTwoDoseUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(cbbe.residentAdministeredTwoDose), 0) AS integer) FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateStaffAdministeredTwoDoseUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT cbbe, se, cdate, cmonth, cday FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate"
    )
    List<CovidBehindBarsEntity> getAllCovidBehindBarsMetricsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT cbbe, se, cdate, cmonth, cday FROM CovidBehindBarsEntity cbbe " +
            "INNER JOIN FETCH StateEntity se ON se.id = cbbe.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cbbe.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate AND se.shortName in (:states)"
    )
    List<CovidBehindBarsEntity> getAllStateCovidBehindBarsMetricsOnOrAfterDate(List<String> states, Date startDate);
}
