package pl.wiktor.todosgui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.wiktor.todosgui.JavaFxLauncher;
import pl.wiktor.todosgui.service.ExceptionHandler;
import pl.wiktor.todosgui.service.TaskService;
import pl.wiktor.todosgui.service.model.TaskBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private static final String REST_API_BASE_LINK = "http://localhost:9090";

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<TaskBO> findAll() {

        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks", TaskBO[].class);
            log.info("[findAll]: Trying to fetch data.");
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                List<TaskBO> fetched = Arrays.asList((TaskBO[]) tasksResponse.getBody());
                log.info("[findAll]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Number of records: " + fetched.size());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findAll]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[findAll]: Fetching failed. Caused by: " + e.getMessage());
            if (JavaFxLauncher.getMainStage() != null) {
                ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<TaskBO> findAllByStatus(String taskStatus) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks?status=" + taskStatus, TaskBO[].class);
            log.info("[findAllByStatus]: Trying to fetch data by status: " + taskStatus);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                List<TaskBO> fetched = Arrays.asList((TaskBO[]) tasksResponse.getBody());
                log.info("[findAllByStatus]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Number of records: " + fetched.size());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findAllByStatus]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[findAllByStatus]: Fetching failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public TaskBO findById(Long id) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks/" + id, TaskBO.class);
            log.info("[findById]: Trying to fetch data by ID: " + id);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                TaskBO fetched = (TaskBO) tasksResponse.getBody();
                log.info("[findById]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findById]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[findById]: Fetching failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TaskBO findByUUID(String uuid) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks/" + uuid, TaskBO.class);
            log.info("[findByUUID]: Trying to fetch data by UUID: " + uuid);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                TaskBO fetched = (TaskBO) tasksResponse.getBody();
                log.info("[findByUUID]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findByUUID]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[findByUUID]: Fetching failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Fetching failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TaskBO add(TaskBO taskBO) {
        try {
            HttpEntity<TaskBO> httpEntity = getTaskHttpEntity(taskBO);
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks", HttpMethod.POST, httpEntity, TaskBO.class);
            log.info("[add]: Trying to create data: " + taskBO.toString());
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                TaskBO fetched = (TaskBO) tasksResponse.getBody();
                log.info("[add]: Creation success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[add]: Creation failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Creation failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[add]: Creation failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Creation failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TaskBO modify(String uuid, TaskBO TaskBO) {
        try {
            HttpEntity<TaskBO> httpEntity = getTaskHttpEntity(TaskBO);
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks/" + uuid, HttpMethod.PUT, httpEntity, TaskBO.class);
            log.info("[modify]: Trying to modify data: " + TaskBO.toString());
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                TaskBO fetched = (TaskBO) tasksResponse.getBody();
                log.info("[modify]: Modification success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[modify]: Modification failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Modification failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[modify]: Modification failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Modification failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(String uuid) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks/" + uuid, HttpMethod.DELETE, null, Boolean.class);
            log.info("[delete]: Trying to delete data: " + uuid);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Boolean fetched = (Boolean) tasksResponse.getBody();
                log.info("[delete]: Deletion success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[delete]: Deletion failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
            ExceptionHandler.resolve("Deletion failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[delete]: Deletion failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Deletion failed.", "Caused by: " + e.getMessage());
        }
        return false;
    }

    private HttpEntity<TaskBO> getTaskHttpEntity(TaskBO taskBO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(taskBO, headers);
    }
}
