package com.projectrefocus.service.geography;

import com.projectrefocus.service.geography.dto.CityDto;
import com.projectrefocus.service.geography.dto.CountyDto;
import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.dto.StateDto;
import com.projectrefocus.service.geography.service.GeographyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/geography")
public class GeographyController {

    private final GeographyService geographyService;

    public GeographyController(GeographyService geographyService) {
        this.geographyService = geographyService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<GeographyDto> getGeography(
            @RequestParam(value = "categoryId") String categoryId,
            @RequestParam(value = "geographyId", required = false) String geographyId
    ) {
        return geographyService.getGeography(categoryId, geographyId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/states")
    public List<StateDto> getStatesByCategory(@RequestParam(value = "categoryId") String categoryId) {
        return geographyService.getStatesByCategory(categoryId);
    }

    @RequestMapping(method = RequestMethod.GET, value ="/counties")
    public List<CountyDto> getCountiesByState(@RequestParam(value = "categoryId") String categoryId, @RequestParam(value = "stateId") String stateId) {
        return geographyService.getCountiesByCategory(categoryId, stateId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public List<CityDto> getCitiesByCounty(@RequestParam(value = "categoryId") String categoryId, @RequestParam(value = "countyId") String countyId) {
        return geographyService.getCitiesByCategory(categoryId, countyId);
    }
}
