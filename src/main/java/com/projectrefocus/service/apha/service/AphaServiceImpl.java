package com.projectrefocus.service.apha.service;

import com.projectrefocus.service.apha.repository.RacismDeclarationRepository;
import com.projectrefocus.service.apha.utils.AphaTransformer;
import com.projectrefocus.service.calendar.entity.CalendarDateEntity;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AphaServiceImpl implements AphaService {

    private final RacismDeclarationRepository racismDeclarationRepository;

    public AphaServiceImpl(RacismDeclarationRepository racismDeclarationRepository) {
        this.racismDeclarationRepository = racismDeclarationRepository;
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<CalendarDateEntity> declarations = fetchForAllStates ?
                racismDeclarationRepository.getAllDeclarationsOnOrAfterDate(startDate) :
                racismDeclarationRepository.getStateDeclarationsOnOrAfterDate(states, startDate);
        if (DataOrientation.cumulative.equals(orientation)) {
            Integer startingAggregate = fetchForAllStates ?
                    racismDeclarationRepository.aggregatedDeclarationsUntilDate(startDate) :
                    racismDeclarationRepository.aggregatedStateDeclarationsUntilDate(states, startDate);
            return AphaTransformer.toCumulativeDeclarations(declarations, startingAggregate);
        }
        return new ArrayList<>();
    }
}
