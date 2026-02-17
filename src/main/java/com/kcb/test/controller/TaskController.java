package com.kcb.test.controller;

import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.entity.Task;
import com.kcb.test.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PutMapping("/{taskId}")
    public Task update(@PathVariable Long taskId,
                       @Valid @RequestBody CreateTaskRequest request) {
        return taskService.update(taskId, request);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }
}
