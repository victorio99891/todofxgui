package pl.wiktor.todosgui.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.wiktor.todosgui.model.Task;
import pl.wiktor.todosgui.service.TaskService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private static final String REST_API_BASE_LINK = "http://localhost:9090";

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Task> findAll() {

        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks", Task[].class);
            log.info("[findAll]: Trying to fetch data.");
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                List<Task> fetched = Arrays.asList((Task[]) tasksResponse.getBody());
                log.info("[findAll]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Number of records: " + fetched.size());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findAll]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Task> findAllByStatus(String taskStatus) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks?status=" + taskStatus, Task[].class);
            log.info("[findAllByStatus]: Trying to fetch data.");
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                List<Task> fetched = Arrays.asList((Task[]) tasksResponse.getBody());
                log.info("[findAllByStatus]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Number of records: " + fetched.size());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findAllByStatus]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return new ArrayList<>();
    }

    @Override
    public Task findById(Long id) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks/" + id, Task.class);
            log.info("[findById]: Trying to fetch data.");
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
                log.info("[findById]: Fetching success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[findById]: Fetching failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public Task add(Task task) {
        try {
            HttpEntity<Task> httpEntity = getTaskHttpEntity(task);
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks", HttpMethod.POST, httpEntity, Task.class);
            log.info("[add]: Trying to create data: " + task.toString());
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
                log.info("[add]: Creation success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[add]: Creation failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public Task modify(Long id, Task task) {
        try {
            HttpEntity<Task> httpEntity = getTaskHttpEntity(task);
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks/" + id, HttpMethod.PUT, httpEntity, Task.class);
            log.info("[modify]: Trying to modify data: " + task.toString());
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
                log.info("[modify]: Modification success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[modify]: Modification failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks/" + id, HttpMethod.DELETE, null, Boolean.class);
            log.info("[delete]: Trying to delete data: " + id);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Boolean fetched = (Boolean) tasksResponse.getBody();
                log.info("[delete]: Deletion success. Status: " + tasksResponse.getStatusCode().toString() + " Data: " + fetched.toString());
                return fetched;
            }
        } catch (HttpClientErrorException e) {
            log.error("[delete]: Deletion failed. Status: " + e.getStatusCode().toString() + " Caused by: " + e.getResponseBodyAsString());
        }
        return false;
    }

    private HttpEntity<Task> getTaskHttpEntity(Task task) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(task, headers);
    }
}
