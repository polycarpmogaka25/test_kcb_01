package com.kcb.test.repository;

import com.kcb.test.TaskStatus;
import com.kcb.test.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByProjectId(
            Long projectId,
            Pageable pageable
    );

    Page<Task> findByProjectIdAndStatus(
            Long projectId,
            TaskStatus status,
            Pageable pageable
    );

    Page<Task> findByProjectIdAndDueDate(
            Long projectId,
            LocalDate dueDate,
            Pageable pageable
    );
}
