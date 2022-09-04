package com.projectrefocus.service.population.service;

import com.projectrefocus.service.population.entity.StatePopulationEntity;
import com.projectrefocus.service.population.repository.StatePopulationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final StatePopulationRepository statePopulationRepository;

    public PopulationServiceImpl(StatePopulationRepository statePopulationRepository) {
        this.statePopulationRepository = statePopulationRepository;
    }

    public Integer aggregatedPopulation(List<String> states) {
        boolean fetchDataForAllStates = states.isEmpty();
        List<StatePopulationEntity> statePopulationEntities = fetchDataForAllStates ?
                statePopulationRepository.findAll() :
                statePopulationRepository.getPopulationForStates(states);

        return statePopulationEntities
                .stream()
                .map(StatePopulationEntity::getPopulation)
                .reduce(0, (Integer::sum));
    }
}
