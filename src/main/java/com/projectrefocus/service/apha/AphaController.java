package com.projectrefocus.service.apha;

import com.projectrefocus.service.apha.service.AphaService;
import com.projectrefocus.service.common.dto.MetricDto;
import com.projectrefocus.service.request.enums.DataOrientation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/apha")
public class AphaController {

    private final AphaService aphaService;

    public AphaController(AphaService aphaService) {
        this.aphaService = aphaService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/racism-declarations")
    public List<MetricDto> getRacismDeclarations(
            @RequestParam(name = "states") List<String> states,
            @RequestParam(name = "orientation")DataOrientation orientation,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return aphaService.getData(states, orientation, startDate);
    }
}
