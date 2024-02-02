package com.projectrefocus.service.dundas;

import com.projectrefocus.service.dundas.dto.IndicatorCategoryDto;
import com.projectrefocus.service.dundas.service.DundasService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dundas")
public class DundasController {

    private final DundasService dundasService;

    public DundasController(DundasService dundasService) {
        this.dundasService = dundasService;
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<IndicatorCategoryDto> getCategories(@RequestParam(required = false) List<String> geographies) {
        return dundasService.getCategories();
    }
}
