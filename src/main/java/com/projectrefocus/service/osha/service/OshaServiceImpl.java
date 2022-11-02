package com.projectrefocus.service.osha.service;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.osha.entity.OshaComplaintsCalendarDateEntity;
import com.projectrefocus.service.osha.repository.OshaComplaintsRepository;
import com.projectrefocus.service.osha.utils.OshaTransformer;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OshaServiceImpl implements OshaService {

    private final OshaComplaintsRepository oshaComplaintsRepository;

    public OshaServiceImpl(OshaComplaintsRepository oshaComplaintsRepository) {
        this.oshaComplaintsRepository = oshaComplaintsRepository;
    }

    public List<MetricDto> getData(List<String> states, DataOrientation orientation, Date startDate) {
        boolean fetchForAllStates = states.isEmpty();
        List<OshaComplaintsCalendarDateEntity> complaints = fetchForAllStates ?
                oshaComplaintsRepository.getAllComplaintsOnOrAfterDate(startDate) :
                oshaComplaintsRepository.getStateComplaintsOnOrAfterDate(states, startDate);
        if (DataOrientation.cumulative.equals(orientation)) {
            Integer startingAggregate = fetchForAllStates ?
                    oshaComplaintsRepository.aggregatedComplaintsUntilDate(startDate) :
                    oshaComplaintsRepository.aggregatedComplaintsUntilDate(states, startDate);
            return OshaTransformer.toCumulativeComplaints(complaints, startingAggregate);
        }

        return new ArrayList<>();
    }
}
