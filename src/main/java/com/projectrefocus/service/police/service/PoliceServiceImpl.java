package com.projectrefocus.service.police.service;

import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.police.repository.FatalPoliceShootingsRepository;
import com.projectrefocus.service.police.utils.PoliceTransformer;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class PoliceServiceImpl implements PoliceService {

    private final FatalPoliceShootingsRepository fatalPoliceShootingsRepository;

    public PoliceServiceImpl(FatalPoliceShootingsRepository fatalPoliceShootingsRepository) {
        this.fatalPoliceShootingsRepository = fatalPoliceShootingsRepository;
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CalendarDateEntity> shootings = fetchForAllStates ?
                fatalPoliceShootingsRepository.getAllShootingsOnOrAfterDate(startDate) :
                fatalPoliceShootingsRepository.getStateShootingsOnOrAfterDate(states, startDate);
        if (DataOrientation.cumulative.equals(orientation)) {
            Integer startingAggregate = fetchForAllStates ?
                    fatalPoliceShootingsRepository.aggregatedShootingsUntilDate(startDate) :
                    fatalPoliceShootingsRepository.aggregatedStateShootingsUntilDate(states, startDate);
            return PoliceTransformer.toCumulativeShootings(shootings, startingAggregate);
        }
        return new ArrayList<>();
    }
}
