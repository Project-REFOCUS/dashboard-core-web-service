package com.projectrefocus.service.geography;

import com.projectrefocus.service.geography.dto.GeographyDto;
import com.projectrefocus.service.geography.enums.GeographyType;
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
            @RequestParam(value = "geographyId", required = false) String geographyId,
            @RequestParam(value = "geographyType", required = false) GeographyType geographyType
    ) {
        return geographyService.getGeography(categoryId, geographyId, geographyType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/states")
    public List<GeographyDto> getStates() {
        return geographyService.getStates();
    }
}
