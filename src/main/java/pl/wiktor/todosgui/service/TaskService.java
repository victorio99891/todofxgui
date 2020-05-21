package pl.wiktor.todosgui.service;

import pl.wiktor.todosgui.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> findAllByStatus(String taskStatus);

    Task findById(Long id);

    Task add(Task task);

    Task modify(Long id, Task task);

    boolean delete(Long id);
}