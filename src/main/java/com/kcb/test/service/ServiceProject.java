package com.kcb.test.service;

import com.kcb.test.dto.CreateProjectRequest;
import com.kcb.test.dto.ProjectSummaryDTO;
import com.kcb.test.entity.Project;
import jakarta.validation.Valid;

import java.util.List;

public interface ServiceProject {
    Project create(@Valid CreateProjectRequest request);

    List<Project> getAll();

    Project getById(Long projectId);

    List<ProjectSummaryDTO> getSummary();
}
