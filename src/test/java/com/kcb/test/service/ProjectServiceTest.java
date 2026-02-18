package com.kcb.test.service;

import com.kcb.test.TaskStatus;
import com.kcb.test.dto.ProjectSummaryDTO;
import com.kcb.test.entity.Project;
import com.kcb.test.entity.Task;
import com.kcb.test.exception.ResourceNotFoundException;
import com.kcb.test.repository.ProjectRepository;
import com.kcb.test.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void test_get_all() {

        when(projectRepository.findAll()).thenReturn(List.of(getProject()));

        var result = projectService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result);

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void test_get_project_by_id() {

        when(projectRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(getProject()));

        var result = projectService.getById(1L);

        Assertions.assertNotNull(result);

        verify(projectRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void test_get_project_by_id_throws_exception_when_not_found() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> projectService.getById(1L));

        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void test_get_summary_success() {
        var project = new Project();
        project.setId(101L);
        project.setName("Alpha Project");

        var t = new Task();
        t.setId(102L);
        t.setStatus(TaskStatus.IN_PROGRESS);
        t.setProject(project);
        t.setTitle("Alpha Task");
        t.setDescription("description");
        project.setTasks(List.of(t));

        when(projectRepository.findAll()).thenReturn(List.of(project));

        var result = projectService.getSummary();

        Assertions.assertEquals(1, result.size());
        var summary = result.getFirst();

        Assertions.assertEquals("Alpha Project", summary.getProjectName());

        var counts = summary.getTaskCountByStatus();

        verify(projectRepository, times(1)).findAll();
    }

    private Project getProject() {
        return Project.builder()
                .id(1L)
                .name("Test Project")
                .description("Test Project")
                .build();
    }
}
