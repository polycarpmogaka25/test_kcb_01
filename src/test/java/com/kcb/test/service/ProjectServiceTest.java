package com.kcb.test.service;

import com.kcb.test.entity.Project;
import com.kcb.test.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void test_get_all() {

        when(projectRepository.findAll()).thenReturn(List.of(getProject()));

        var result = projectService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result);

        verify(projectRepository, times(1)).findAll();
    }

    private Project getProject() {
        return Project.builder()
                .name("Test Project")
                .description("Test Project")
                .build();
    }
}
