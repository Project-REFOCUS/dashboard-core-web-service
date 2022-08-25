package com.projectrefocus.service.ethnicity;

import com.projectrefocus.service.ethnicity.dto.RaceEthnicityDto;
import com.projectrefocus.service.ethnicity.service.RaceEthnicityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ethnicity")
public class EthnicityController {

    private final RaceEthnicityService raceEthnicityService;

    public EthnicityController(RaceEthnicityService raceEthnicityService) {
        this.raceEthnicityService = raceEthnicityService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<RaceEthnicityDto> getRaceEthnicityCategories() {
        return raceEthnicityService.getRaceEthnicityCategories();
    }
}
