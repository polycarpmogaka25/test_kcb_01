package com.kcb.test.controller;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.CreateProjectRequest;
import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.dto.ProjectSummaryDTO;
import com.kcb.test.entity.Project;
import com.kcb.test.entity.Task;
import com.kcb.test.service.ServiceProject;
import com.kcb.test.service.ServiceTask;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ServiceProject projectService;

    private final ServiceTask taskService;

    @PostMapping
    public ResponseEntity<Project> create(@Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.create(request));
    }

    @GetMapping
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/{projectId}")
    public Project getById(@PathVariable Long projectId) {
        return projectService.getById(projectId);
    }

    @PostMapping("/{projectId}/tasks")
    public Task createTask(@PathVariable Long projectId,
                           @Valid @RequestBody CreateTaskRequest request) {
        return taskService.create(projectId, request);
    }

    @GetMapping("/{projectId}/tasks")
    public Page<Task> getTasks(
            @PathVariable Long projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int  size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {

        return taskService.getTasks(projectId, status, dueDate, page,size);
    }

    @GetMapping("/summary")
    public List<ProjectSummaryDTO> summary() {
        return projectService.getSummary();
    }

}
