package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateDeathsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CovidStateDeathsRepository extends JpaRepository<CovidStateDeathsEntity, Integer> {

    @Query(
            "select csde, se, cdate, cday, cmonth from CovidStateDeathsEntity csde " +
            "INNER JOIN FETCH StateEntity se ON se.id = csde.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csde.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cdate.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "where se.id = :stateId"
    )
    List<CovidStateDeathsEntity> getAllByStateId(Byte stateId);
}
