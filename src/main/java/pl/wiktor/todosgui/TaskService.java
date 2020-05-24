package pl.wiktor.todosgui;

import pl.wiktor.todosgui.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> findAllByStatus(String taskStatus);

    Task findById(Long id);

    Task findByUUID(String uuid);

    Task add(Task task);

    Task modify(String uuid, Task task);

    boolean delete(String uuid);
}
