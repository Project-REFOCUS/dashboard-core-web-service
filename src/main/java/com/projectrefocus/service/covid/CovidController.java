package com.projectrefocus.service.covid;

import com.projectrefocus.service.covid.dto.CovidStateCasesDto;
import com.projectrefocus.service.covid.dto.CovidStateDeathsDto;
import com.projectrefocus.service.covid.dto.CovidStateTestsDto;
import com.projectrefocus.service.covid.service.CovidService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/covid")
public class CovidController {

    private final CovidService covidService;

    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cases")
    public List<CovidStateCasesDto> getCovidCases(@RequestParam(name = "stateId") Byte stateId) {
        return covidService.getCasesByState(stateId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deaths")
    public List<CovidStateDeathsDto> getCovidDeaths(@RequestParam(name = "stateId") Byte stateId) {
        return covidService.getDeathsByState(stateId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tests")
    public List<CovidStateTestsDto> getCovidTests(@RequestParam(name = "stateId") Byte stateId) {
        return covidService.getTestsByState(stateId);
    }
}
