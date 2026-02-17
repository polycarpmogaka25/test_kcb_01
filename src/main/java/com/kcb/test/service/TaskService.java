package com.kcb.test.service;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.entity.Task;
import com.kcb.test.exception.ResourceNotFoundException;
import com.kcb.test.repository.ProjectRepository;
import com.kcb.test.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService implements ServiceTask {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Task create(Long projectId, CreateTaskRequest request) {

        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        var task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.TO_DO)
                .dueDate(request.getDueDate())
                .project(project)
                .build();
        return taskRepository.save(task);

    }

    @Override
    public Page<Task> getTasks(Long projectId,
                               TaskStatus status,
                               LocalDate dueDate,int page,int size) {
        var pageable = PageRequest.of(page, size);

        if (status != null)
            return taskRepository.findByProjectIdAndStatus(projectId, status, pageable);

        if (dueDate != null)
            return taskRepository.findByProjectIdAndDueDate(projectId, dueDate, pageable);

        return taskRepository.findByProjectId(projectId, pageable);
    }

    public Task update(Long taskId, CreateTaskRequest request) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDueDate());

        return taskRepository.save(task);
    }

    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
