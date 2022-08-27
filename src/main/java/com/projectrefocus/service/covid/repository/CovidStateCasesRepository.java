package com.projectrefocus.service.covid.repository;

import com.projectrefocus.service.covid.entity.CovidStateCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CovidStateCasesRepository extends JpaRepository<CovidStateCasesEntity, Integer> {

    @Query(
            "select csce, se, cdate, cday, cmonth from CovidStateCasesEntity csce " +
            "INNER JOIN FETCH StateEntity se ON se.id = csce.state.id " +
            "INNER JOIN FETCH CalendarDateEntity cdate ON cdate.id = csce.date.id " +
            "INNER JOIN FETCH CalendarMonthEntity cmonth ON cmonth.id = cdate.month.id " +
            "INNER JOIN FETCH CalendarDayEntity cday ON cday.id = cdate.day.id " +
            "where se.shortName in :states"
    )
    List<CovidStateCasesEntity> getDailyCasesForStates(List<String> states);
}
