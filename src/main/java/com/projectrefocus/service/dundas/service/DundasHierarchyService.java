package com.projectrefocus.service.dundas.service;

import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberRequestEntity;

import java.util.List;

public interface DundasHierarchyService {

    List<HierarchyMemberEntity> getHierarchyMembers(String analysisStructureId, String memberUniqueName);
}
