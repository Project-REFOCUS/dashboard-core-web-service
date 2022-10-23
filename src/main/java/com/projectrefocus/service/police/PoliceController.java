package com.projectrefocus.service.police;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.police.service.PoliceService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/police")
public class PoliceController {

    private final PoliceService policeService;

    public PoliceController(PoliceService policeService) {
        this.policeService = policeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shootings")
    public List<MetricDto> getFatalPoliceShootings(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation") DataOrientation orientation,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return policeService.getData(states, orientation, startDate);
    }
}
