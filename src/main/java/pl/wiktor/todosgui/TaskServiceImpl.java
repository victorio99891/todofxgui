package pl.wiktor.todosgui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.wiktor.todosgui.JavaFxLauncher;
import pl.wiktor.todosgui.Task;
import pl.wiktor.todosgui.ExceptionHandler;
import pl.wiktor.todosgui.TaskService;

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
    public List<Task> findAllByStatus(String taskStatus) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks?status=" + taskStatus, Task[].class);
            log.info("[findAllByStatus]: Trying to fetch data by status: " + taskStatus);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                List<Task> fetched = Arrays.asList((Task[]) tasksResponse.getBody());
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
    public Task findById(Long id) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks/" + id, Task.class);
            log.info("[findById]: Trying to fetch data by ID: " + id);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
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
    public Task findByUUID(String uuid) {
        try {
            ResponseEntity<?> tasksResponse = restTemplate.getForEntity(REST_API_BASE_LINK + "/tasks/" + uuid, Task.class);
            log.info("[findByUUID]: Trying to fetch data by UUID: " + uuid);
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
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
            ExceptionHandler.resolve("Creation failed.", "Caused by: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[add]: Creation failed. Caused by: " + e.getMessage());
            ExceptionHandler.resolve("Creation failed.", "Caused by: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Task modify(String uuid, Task task) {
        try {
            HttpEntity<Task> httpEntity = getTaskHttpEntity(task);
            ResponseEntity<?> tasksResponse = restTemplate.exchange(REST_API_BASE_LINK + "/tasks/" + uuid, HttpMethod.PUT, httpEntity, Task.class);
            log.info("[modify]: Trying to modify data: " + task.toString());
            if (tasksResponse.getStatusCode().value() == 200 && tasksResponse.getBody() != null) {
                Task fetched = (Task) tasksResponse.getBody();
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

    private HttpEntity<Task> getTaskHttpEntity(Task task) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(task, headers);
    }
}
