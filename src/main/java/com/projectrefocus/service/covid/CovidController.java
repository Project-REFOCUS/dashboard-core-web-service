package com.projectrefocus.service.covid;

import com.projectrefocus.service.covid.dto.CovidMetricDto;
import com.projectrefocus.service.covid.service.CovidService;
import com.projectrefocus.service.request.enums.DataOrientation;
import com.projectrefocus.service.request.enums.SubCategory;
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
    public List<CovidMetricDto> getCovidCases(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation") DataOrientation orientation,
            // TODO: Look into a solution for fixing timezone issue creating off by one error
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return covidService.getCovidCasesData(states, orientation, startDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deaths")
    public List<CovidMetricDto> getCovidDeaths(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation") DataOrientation orientation,
            // TODO: Look into a solution for fixing timezone issue creating off by one error
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return covidService.getCovidDeathsData(states, orientation, startDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tests")
    public List<CovidMetricDto> getCovidTests(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation") DataOrientation orientation,
            // TODO: Look into a solution for fixing timezone issue creating off by one error
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return covidService.getCovidTestsData(states, orientation, startDate);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vaccinations")
    public List<CovidMetricDto> getCovidVaccinations(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "subCategory") SubCategory subCategory,
            @RequestParam(name = "orientation") DataOrientation orientation,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return covidService.getCovidVaccinationsData(states, subCategory, orientation, startDate);
    }

}
