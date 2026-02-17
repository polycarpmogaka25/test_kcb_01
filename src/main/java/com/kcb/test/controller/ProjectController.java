package com.kcb.test.controller;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.CreateProjectRequest;
import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.dto.ProjectSummaryDTO;
import com.kcb.test.entity.Project;
import com.kcb.test.entity.Task;
import com.kcb.test.service.ServiceProject;
import com.kcb.test.service.ServiceTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Project Management", description = "Operations related to Projects and their associated Tasks")
public class ProjectController {

    private final ServiceProject projectService;
    private final ServiceTask taskService;

    @PostMapping
    @Operation(summary = "Create a new project", description = "Creates a project with a name and optional description")
    @ApiResponse(responseCode = "200", description = "Project created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<Project> create(@Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.create(request));
    }

    @GetMapping
    @Operation(summary = "List all projects", description = "Retrieves a full list of projects along with their tasks")
    @ApiResponse(responseCode = "200", description = "Successful retrieval")
    public List<Project> getAll() {
        return projectService.getAll();
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "Get project by ID", description = "Retrieves detailed information for a specific project")
    @ApiResponse(responseCode = "200", description = "Project found")
    @ApiResponse(responseCode = "404", description = "Project not found")
    public Project getById(@PathVariable Long projectId) {
        return projectService.getById(projectId);
    }

    @PostMapping("/{projectId}/tasks")
    @Operation(summary = "Add task to project", description = "Creates a new task associated with the given project ID")
    @ApiResponse(responseCode = "200", description = "Task created successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    public Task createTask(@PathVariable Long projectId,
                           @Valid @RequestBody CreateTaskRequest request) {
        return taskService.create(projectId, request);
    }

    @GetMapping("/{projectId}/tasks")
    @Operation(summary = "Get project tasks", description = "Retrieves a paginated list of tasks for a project, with optional status and due date filtering")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    public Page<Task> getTasks(
            @PathVariable Long projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {

        return taskService.getTasks(projectId, status, dueDate, page, size);
    }

    @GetMapping("/summary")
    @Operation(summary = "Get project summaries", description = "Returns a list of all projects with counts of tasks grouped by their status")
    @ApiResponse(responseCode = "200", description = "Summary generated successfully")
    public List<ProjectSummaryDTO> summary() {
        return projectService.getSummary();
    }
}