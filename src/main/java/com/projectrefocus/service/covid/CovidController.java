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
    public List<CovidStateCasesDto> getCovidCases(@RequestParam(name = "states") List<String> states) {
        return covidService.getCasesByState(states);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deaths")
    public List<CovidStateDeathsDto> getCovidDeaths(@RequestParam(name = "states") List<String> states) {
        return covidService.getDeathsByState(states);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tests")
    public List<CovidStateTestsDto> getCovidTests(@RequestParam(name = "states") List<String> states) {
        return covidService.getTestsByState(states);
    }
}
