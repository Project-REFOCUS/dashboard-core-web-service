package com.projectrefocus.service.geography;

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

    @RequestMapping(method = RequestMethod.GET, value = "/states")
    public List<StateDto> getUSStates() {
        return geographyService.getUSStates();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/counties")
    public String getCountiesByState(@RequestParam(value = "stateId") Byte stateId) {
        return String.format("{'us_county': 0 in state %s}", stateId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public String getCitiesByCounty(@RequestParam(value = "countyId") Short countyId) {
        return String.format("{'us_city': 0 in county %s}", countyId);
    }
}
