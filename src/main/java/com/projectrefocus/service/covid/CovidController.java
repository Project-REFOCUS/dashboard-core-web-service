package com.projectrefocus.service.covid;

import com.projectrefocus.service.covid.dto.CovidStateMetricDto;
import com.projectrefocus.service.covid.service.CovidService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/covid")
public class CovidController {

    private final CovidService covidService;

    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cases")
    public List<CovidStateMetricDto> getCovidCases(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate) {
        return covidService.getCovidCasesData(states, startDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deaths")
    public List<CovidStateMetricDto> getCovidDeaths(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate) {
        return covidService.getCovidDeathsData(states, startDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tests")
    public List<CovidStateMetricDto> getCovidTests(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate) {
        return covidService.getCovidTestsData(states, startDate);
    }
}
