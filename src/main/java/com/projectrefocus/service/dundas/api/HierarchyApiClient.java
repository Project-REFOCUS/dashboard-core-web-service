package com.projectrefocus.service.dundas.api;

import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberRequestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Hierarchy", url = "${dundas.api.baseurl}/Hierarchy")
public interface HierarchyApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/members/{analysisStructureId}")
    List<HierarchyMemberEntity> getHierarchyMembers(
            @RequestHeader(name = "Authorization") String sessionId,
            @PathVariable(name = "analysisStructureId") String analysisStructureId,
            @RequestBody HierarchyMemberRequestEntity hierarchyMemberRequest
    );
}
