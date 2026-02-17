package com.kcb.test.dto;

import com.kcb.test.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSummaryDTO {
    private Long projectId;
    private String projectName;
    private Map<TaskStatus, Long> taskCountByStatus;
}
