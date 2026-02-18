package com.kcb.test.service.impl;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.CreateProjectRequest;
import com.kcb.test.dto.ProjectSummaryDTO;
import com.kcb.test.entity.Project;
import com.kcb.test.entity.Task;
import com.kcb.test.exception.ResourceNotFoundException;
import com.kcb.test.repository.ProjectRepository;
import com.kcb.test.service.ServiceProject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ServiceProject {

    private final ProjectRepository projectRepository;

    @Override
    public Project create(CreateProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    @Override
    public List<ProjectSummaryDTO> getSummary() {
        return projectRepository.findAll().stream()
                .map(project -> {
                    Map<TaskStatus, Long> counts = project.getTasks().stream()
                            .collect(Collectors.groupingBy(
                                    Task::getStatus,
                                    Collectors.counting()
                            ));
                    return new ProjectSummaryDTO(project.getId(), project.getName(), counts);
                })
                .toList();
    }
}
