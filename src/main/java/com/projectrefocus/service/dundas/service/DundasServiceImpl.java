package com.projectrefocus.service.dundas.service;

import com.projectrefocus.service.dundas.api.*;
import com.projectrefocus.service.dundas.dto.*;
import com.projectrefocus.service.dundas.entity.*;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberEntity;
import com.projectrefocus.service.dundas.entity.hierarchy.HierarchyMemberRequestEntity;
import com.projectrefocus.service.dundas.entity.metricset.MetricSetParameterHierarchyEntity;
import com.projectrefocus.service.dundas.entity.metricset.MetricSetRowHierarchyParameterEntity;
import com.projectrefocus.service.dundas.enums.DashboardFileObjectType;
import com.projectrefocus.service.dundas.enums.GraphType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.projectrefocus.service.dundas.enums.DashboardFileObjectType.Dashboard;
import static com.projectrefocus.service.dundas.enums.DashboardFileObjectType.DashboardsFolder;


@Service
public class DundasServiceImpl implements DundasService, DundasFileService, DundasDashboardService, DundasMetricSetService, DundasHierarchyService {

    @Value("${dundas.api.username}")
    private String username;

    @Value("${dundas.api.password}")
    private String password;

    private static String SessionId;
    private final LoginApiClient loginApiClient;
    private final ProjectApiClient projectApiClient;
    private final SessionApiClient sessionApiClient;
    private final FileApiClient fileApiClient;
    private final DashboardApiClient dashboardApiClient;
    private final MetricSetApiClient metricSetApiClient;
    private final HierarchyApiClient hierarchyApiClient;

    public DundasServiceImpl(LoginApiClient loginApiClient, ProjectApiClient projectApiClient, SessionApiClient sessionApiClient, FileApiClient fileApiClient, DashboardApiClient dashboardApiClient, MetricSetApiClient metricSetApiClient, HierarchyApiClient hierarchyApiClient) {
        this.loginApiClient = loginApiClient;
        this.projectApiClient = projectApiClient;
        this.sessionApiClient = sessionApiClient;
        this.dashboardApiClient = dashboardApiClient;
        this.fileApiClient = fileApiClient;
        this.metricSetApiClient = metricSetApiClient;
        this.hierarchyApiClient = hierarchyApiClient;
    }

    private void login() {
        LoginDto loginDto = new LoginDto();
        loginDto.setAccountName(username);
        loginDto.setPassword(password);
        LogOnResponseEntity response = loginApiClient.login(new LogOnRequestEntity(loginDto));
        DundasServiceImpl.SessionId = response.getSessionId();
    }

    private boolean isSessionValid() {
        try {
            ResponseEntity<SessionResponseEntity> response = sessionApiClient.getSession(SessionId, new SessionDetailsRequestEntity(SessionId));
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void ensureValidSession() {
        if (!isSessionValid()) {
            login();
        }
    }

    private ProjectDetailsResponseEntity getProjectDetails() {
        ensureValidSession();

        List<ProjectResponseEntity> projectList = projectApiClient.getProjects("None", SessionId);
        ProjectResponseEntity projectRefocus = projectList.stream().filter(p -> p.getName().equals("Project REFOCUS")).findFirst().orElse(null);
        if (projectRefocus == null) {
            throw new RuntimeException("Could not find Project REFOCUS Dundas project");
        }
        String projectRefocusProjectId = projectRefocus.getProjectId();
        return projectApiClient.getProjectDetailsById(SessionId, projectRefocusProjectId);
    }

    private DashboardFileQueryRequestEntity createDashboardFileQueryRequest(String dashboardParentFolderId) {
        DashboardFileQueryRequestEntity dashboardFileQueryRequest = new DashboardFileQueryRequestEntity();
        dashboardFileQueryRequest.setQueryRootIds(new ArrayList<>() {
            {
                add(dashboardParentFolderId);
            }
        });
        dashboardFileQueryRequest.setOrderBy(new ArrayList<>() {
            {
                add(new DashboardFileSortEntity("IsFolder", "Ascending"));
                add(new DashboardFileSortEntity("Name", "Descending"));
            }
        });
        dashboardFileQueryRequest.setPageNumber(1);
        dashboardFileQueryRequest.setPageSize(150);

        return dashboardFileQueryRequest;
    }

    private void convertDashboardFilesToCategories(List<IndicatorCategoryDto> categories, List<DashboardFileResponseEntity> dashboardFiles) {
        for (DashboardFileResponseEntity dashboardFile : dashboardFiles) {
            List<String> tags = dashboardFile.getTags();
            String idTag = tags.stream().filter(t -> t.startsWith("id:")).findAny().orElse("");
            if (dashboardFile.getObjectType().equals(Dashboard) && tags.contains("Active") && !idTag.isEmpty()) {
                GraphType graphType = tags.contains(GraphType.BarChart.name()) ? GraphType.BarChart : GraphType.LineChart;
                categories.add(new IndicatorCategoryDto(idTag.replace("id:", ""), dashboardFile.getId(), dashboardFile.getName(), graphType));
            }
        }
    }

    public List<IndicatorCategoryDto> getCategories() {
        ProjectDetailsResponseEntity projectRefocusDetails = getProjectDetails();

        DashboardFileQueryRequestEntity dashboardFileRequest = createDashboardFileQueryRequest(projectRefocusDetails.getDashboardsRootFolder().getId());
        List<DashboardFileResponseEntity> dashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileRequest));

        List<IndicatorCategoryDto> categories = new ArrayList<>();
        List<DashboardFileResponseEntity> dashboardFolders = dashboardFiles.stream().filter(f -> f.getObjectType().equals(DashboardsFolder)).toList();
        convertDashboardFilesToCategories(categories, dashboardFiles);

        for (DashboardFileResponseEntity folder : dashboardFolders) {
            DashboardFileQueryRequestEntity dashboardQuery = createDashboardFileQueryRequest(folder.getId());
            List<DashboardFileResponseEntity> files = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardQuery));

            convertDashboardFilesToCategories(categories, files);
        }

        return categories;
    }

    private void getDashboardFilesRecursively(List<DashboardFileResponseEntity> allDashboardFiles, List<DashboardFileObject> activeDashboardFiles) {
        for (DashboardFileResponseEntity dashboardFile : allDashboardFiles) {
            DashboardFileObjectType dashboardObjectType = dashboardFile.getObjectType();
            if (dashboardObjectType.equals(DashboardsFolder)) {
                DashboardFileQueryRequestEntity dashboardFileQueryRequest = createDashboardFileQueryRequest(dashboardFile.getId());
                List<DashboardFileResponseEntity> nestedDashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileQueryRequest));
                getDashboardFilesRecursively(nestedDashboardFiles, activeDashboardFiles);
            } else if (dashboardObjectType.equals(Dashboard)) {
                DashboardFileObject dashboardFileObject = new DashboardFileObject();
                dashboardFileObject.setId(dashboardFile.getId());
                dashboardFileObject.setName(dashboardFile.getName());
                dashboardFileObject.setTags(dashboardFile.getTags());
                dashboardFileObject.setDescription(dashboardFile.getDescription());
                activeDashboardFiles.add(dashboardFileObject);
            }
        }
    }

    public List<DashboardFileObject> getDashboardFiles() {
        ProjectDetailsResponseEntity projectDetails = getProjectDetails();

        DashboardFileQueryRequestEntity dashboardFileRequest = createDashboardFileQueryRequest(projectDetails.getDashboardsRootFolder().getId());
        List<DashboardFileResponseEntity> dashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileRequest));

        List<DashboardFileObject> activeDashboardFiles = new ArrayList<>();
        getDashboardFilesRecursively(dashboardFiles, activeDashboardFiles);

        return activeDashboardFiles;
    }

    private void getActiveDashboardFoldersRecursively(List<DashboardFileResponseEntity> allDashboardFiles, List<DashboardFileObject> activeDashboardFolders) {
        for (DashboardFileResponseEntity dashboardFile : allDashboardFiles) {
            DashboardFileObjectType dashboardObjectType = dashboardFile.getObjectType();
            if (dashboardObjectType.equals(DashboardsFolder) && dashboardFile.getTags().contains("Active")) {
                DashboardFileObject dashboardFileObject = new DashboardFileObject();
                dashboardFileObject.setId(dashboardFile.getId());
                dashboardFileObject.setName(dashboardFile.getName());
                dashboardFileObject.setDescription(dashboardFile.getDescription());
                dashboardFileObject.setTags(dashboardFile.getTags());
                activeDashboardFolders.add(dashboardFileObject);

                DashboardFileQueryRequestEntity dashboardFileQueryRequest = createDashboardFileQueryRequest(dashboardFile.getId());
                List<DashboardFileResponseEntity> nestedDashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileQueryRequest));
                getActiveDashboardFoldersRecursively(nestedDashboardFiles, activeDashboardFolders);
            }
        }
    }

    public List<DashboardFileObject> getActiveDashboardFolders() {
        ProjectDetailsResponseEntity projectDetails = getProjectDetails();

        DashboardFileQueryRequestEntity dashboardFileRequest = createDashboardFileQueryRequest(projectDetails.getDashboardsRootFolder().getId());
        List<DashboardFileResponseEntity> dashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileRequest));

        List<DashboardFileObject> activeDashboardFolders = new ArrayList<>();
        getActiveDashboardFoldersRecursively(dashboardFiles, activeDashboardFolders);

        return activeDashboardFolders;
    }

    public List<DashboardFileObject> getDashboardFilesInFolder(String folderId) {
        ensureValidSession();
        DashboardFileQueryRequestEntity dashboardFileRequest = createDashboardFileQueryRequest(folderId);
        List<DashboardFileResponseEntity> dashboardFiles = fileApiClient.getDashboardFiles(SessionId, new DashboardFileQueryRequestWrapperEntity(dashboardFileRequest));

        return dashboardFiles.stream().map(file -> {
            DashboardFileObject dashboardFileObject = new DashboardFileObject();
            dashboardFileObject.setId(file.getId());
            dashboardFileObject.setName(file.getName());
            dashboardFileObject.setTags(file.getTags());
            dashboardFileObject.setDescription(file.getDescription());

            return dashboardFileObject;
        }).toList();
    }

    public DashboardDetails getDashboardById(String dashboardId) {
        ensureValidSession();
        DashboardDetailsResponseEntity response = dashboardApiClient.getDashboardDetails(SessionId, dashboardId);
        DashboardDetails details = new DashboardDetails();
        details.setId(response.getId());
        details.setName(response.getName());
        details.setClassType(response.getClassType());
        details.setTags(response.getTags());
        details.setAdapters(response.getAdapters());
        return details;
    }

    public MetricSetDetails getMetricSetDetails(String metricSetId) {
        ensureValidSession();

        MetricSetDetailsEntity metricSetDetailsEntity = metricSetApiClient.getMetricSetById(SessionId, metricSetId);
        MetricSetDetails metricSetDetails = new MetricSetDetails();

        MetricSetRowHierarchyParameterEntity parameter = metricSetDetailsEntity.getRowHierarchies().get(0).getParameter();
        MetricSetParameterHierarchyEntity hierarchy = parameter.getHierarchy();
        List<MetricSetHierarchyLevel> metricSetHierarchyLevels = hierarchy.getLevels().stream().map(level -> {
            MetricSetHierarchyLevel metricSetHierarchyLevel = new MetricSetHierarchyLevel();
            metricSetHierarchyLevel.setHierarchyUniqueName(level.getHierarchyUniqueName());
            metricSetHierarchyLevel.setDataSourceId(level.getDataSourceId());
            metricSetHierarchyLevel.setCompatibleUniqueName(level.getCompatibleUniqueName());
            metricSetHierarchyLevel.setUniqueName(level.getUniqueName());

            return metricSetHierarchyLevel;
        }).toList();

        MetricSetHierarchy metricSetHierarchy = new MetricSetHierarchy();
        metricSetHierarchy.setId(parameter.getId());
        metricSetHierarchy.setCaption(hierarchy.getCaption());
        metricSetHierarchy.setName(parameter.getName());
        metricSetHierarchy.setParentEntityId(parameter.getParentEntityId());
        metricSetHierarchy.setLevels(metricSetHierarchyLevels);

        metricSetDetails.setMetricSetHierarchy(metricSetHierarchy);
        metricSetDetails.setId(metricSetDetailsEntity.getId());
        metricSetDetails.setName(metricSetDetailsEntity.getName());
        metricSetDetails.setAnalysisStructureId(metricSetDetailsEntity.getAnalysisStructureId());

        return metricSetDetails;
    }

    public List<HierarchyMemberEntity> getHierarchyMembers(String metricSetId, String memberUniqueName) {
        ensureValidSession();

        MetricSetDetails metricSetDetails = getMetricSetDetails(metricSetId);
        HierarchyMemberRequestEntity request = new HierarchyMemberRequestEntity();
        MetricSetHierarchyLevel hierarchyLevel = metricSetDetails.getMetricSetHierarchy().getLevels().get(0);
        request.setHierarchyUniqueName(hierarchyLevel.getHierarchyUniqueName());
        request.setLevelUniqueName(hierarchyLevel.getUniqueName());
        request.setMemberUniqueName(memberUniqueName);
        return hierarchyApiClient.getHierarchyMembers(SessionId, metricSetDetails.getAnalysisStructureId(), request);
    }
}
