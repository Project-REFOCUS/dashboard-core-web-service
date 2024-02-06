package com.projectrefocus.service.graph;

import com.projectrefocus.service.geography.enums.GeographyType;
import com.projectrefocus.service.graph.dto.Graph;
import com.projectrefocus.service.graph.service.GraphService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/graph")
public class GraphController {

    private final GraphService graphService;
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Graph> getGraphsByCategory(
            @RequestParam(name = "categoryId") String categoryId,
            @RequestParam(name = "geographyType", required = false) GeographyType geographyType
    ) {
        return this.graphService.getGraphsByCategoryId(categoryId, geographyType);
    }
}
