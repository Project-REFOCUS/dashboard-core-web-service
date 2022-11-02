package com.projectrefocus.service.osha;

import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.osha.service.OshaService;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/osha")
public class OshaController {

    private final OshaService oshaService;

    public OshaController(OshaService oshaService) {
        this.oshaService = oshaService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/complaints")
    public List<MetricDto> getOshaComplaints(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation") DataOrientation orientation,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return oshaService.getData(states, orientation, startDate);
    }
}
