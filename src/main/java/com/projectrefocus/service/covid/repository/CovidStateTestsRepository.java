package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateTestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CovidStateTestsRepository extends JpaRepository<CovidStateTestsEntity, Integer> {

    @Query(
          "select cste, se, cdate, cday, cmonth from CovidStateTestsEntity cste " +
          "INNER JOIN FETCH StateEntity se ON se.id = cste.state.id " +
          "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = cste.date.id " +
          "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
          "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
          "where se.id = :stateId"
    )
    List<CovidStateTestsEntity> getAllByStateId(Byte stateId);
}
