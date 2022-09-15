package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateVaccinationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CovidStateVaccinationsRepository extends JpaRepository<CovidStateVaccinationsEntity, Integer> {

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.distributed), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
            "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
            "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedDistributedVaccinationsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.distributed), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateDistributedVaccinationsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administered), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateAdministeredVaccinationsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administered), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedAdministeredVaccinationsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administeredOneDose), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate"
    )
    Integer aggregatedAdministeredOneDoseVaccinationsUntilDate(Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administeredOneDose), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateAdministeredOneDoseVaccinationsUntilDate(List<String> states, Date targetDate);

    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administeredTwoDose), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate AND se.shortName IN (:states)"
    )
    Integer aggregatedStateAdministeredTwoDoseVaccinationsUntilDate(List<String> states, Date targetDate);


    @Query(
            "SELECT CAST(COALESCE(SUM(csve.administeredTwoDose), 0) AS integer) FROM CovidStateVaccinationsEntity csve " +
                    "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
                    "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
                    "WHERE cdate.date <= :targetDate"
    )
    Integer aggregateAdministeredTwoDoseVaccinationsUntilDate(Date targetDate);

    @Query(
            "SELECT csve, se, cdate, cmonth, cday FROM CovidStateVaccinationsEntity csve " +
            "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate"
    )
    List<CovidStateVaccinationsEntity> getAllVaccinationsOnOrAfterDate(Date startDate);

    @Query(
            "SELECT csve, se, cdate, cmonth, cday FROM CovidStateVaccinationsEntity csve " +
            "INNER JOIN FETCH StateEntity se ON se.id = csve.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csve.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "WHERE cdate.date > :startDate AND se.shortName in (:states)"
    )
    List<CovidStateVaccinationsEntity> getStateVaccinationsOnOrAfterDate(List<String> states, Date startDate);
}
