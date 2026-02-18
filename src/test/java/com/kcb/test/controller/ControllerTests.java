package com.kcb.test.controller;

import com.kcb.test.dto.CreateProjectRequest;
import com.kcb.test.entity.Project;
import com.kcb.test.service.ServiceProject;
import com.kcb.test.service.ServiceTask;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ControllerTests {
    @InjectMocks
    private ProjectController controller;
    @InjectMocks
    private TaskController taskController;

    @Mock
    private ServiceProject serviceProject;

    @Mock
    private ServiceTask serviceTask;

    private WebTestClient webTestClient;

    @AfterAll
    static void teardown() {
        Mockito.reset();
    }

    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToController(controller).build();
        // webTestClient = WebTestClient.bindToController(taskController).build();

    }

    @Test
    void test_create_project() {
        when(serviceProject.create(
                any(CreateProjectRequest.class)
        )).thenReturn(getProject());

        var baseUrl = "/projects";

        webTestClient.post().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(getRequest())
                .exchange().expectStatus().isOk();

        verify(serviceProject).create(any(CreateProjectRequest.class));
    }

    @Test
    void test_get_project_by_id() {
        when(serviceProject.getById(
                any(Long.class)
        )).thenReturn(getProject());

        var baseUrl = "/projects/1";

        webTestClient.get().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();

        verify(serviceProject).getById(any(Long.class));
    }

    @Test
    void test_get_all_projects() {
        when(serviceProject.getAll()).thenReturn(List.of(getProject()));

        var baseUrl = "/projects";

        webTestClient.get().uri(baseUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();

        verify(serviceProject).getAll();
    }

    private CreateProjectRequest getRequest() {

        return CreateProjectRequest.builder()
                .name("test")
                .description("test")
                .build();
    }

    private Project getProject() {
        return Project.builder()
                .name("Test Project")
                .description("Test Project")
                .build();
    }

}

