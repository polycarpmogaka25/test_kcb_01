package com.kcb.test.controller;

import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.entity.Task;
import com.kcb.test.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Operations for modifying or removing existing tasks")
public class TaskController {

    private final TaskServiceImpl taskService;


    @PutMapping("/{taskId}")
    @Operation(summary = "Update task details", description = "Updates an existing task's title, description, status, or due date")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @ApiResponse(responseCode = "400", description = "Invalid update data")
    public Task update(@PathVariable Long taskId,
                       @Valid @RequestBody CreateTaskRequest request) {
        return taskService.update(taskId, request);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }
}
