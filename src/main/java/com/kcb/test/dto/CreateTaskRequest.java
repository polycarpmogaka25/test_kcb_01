package com.kcb.test.dto;

import com.kcb.test.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateTaskRequest{
        @NotBlank
        private String title;
        private String description;
        private TaskStatus status;
        @NotNull
        private LocalDate dueDate;
}
