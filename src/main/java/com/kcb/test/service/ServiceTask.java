package com.kcb.test.service;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.CreateTaskRequest;
import com.kcb.test.entity.Task;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface ServiceTask {
    Task create(Long projectId, @Valid CreateTaskRequest request);

    Page<Task> getTasks(Long projectId, TaskStatus status, LocalDate dueDate, int page, int size);
}
