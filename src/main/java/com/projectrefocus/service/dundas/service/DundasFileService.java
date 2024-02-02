package com.projectrefocus.service.dundas.service;

import com.projectrefocus.service.dundas.dto.DashboardFileObject;

import java.util.List;

public interface DundasFileService {

    List<DashboardFileObject> getDashboardFiles();

    List<DashboardFileObject> getActiveDashboardFolders();

    List<DashboardFileObject> getDashboardFilesInFolder(String folderId);
}
