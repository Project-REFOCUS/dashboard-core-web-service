package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.ProjectDetailsResponseEntity;
import com.projectrefocus.service.dundas.entity.ProjectResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Project", url = "${dundas.api.baseurl}/Project")
public interface ProjectApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    List<ProjectResponseEntity> getProjects(@RequestParam(name = "options") String options, @RequestParam(name = "sessionId") String sessionId);

    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}")
    ProjectDetailsResponseEntity getProjectDetailsById(@RequestParam(name = "sessionId") String sessionId, @PathVariable(name = "projectId") String projectId);
}
