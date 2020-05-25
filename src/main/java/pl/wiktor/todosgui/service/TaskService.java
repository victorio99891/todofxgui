package pl.wiktor.todosgui.service;

import pl.wiktor.todosgui.controllers.model.TaskDTO;
import pl.wiktor.todosgui.service.model.TaskBO;

import java.util.List;

public interface TaskService {

    List<TaskBO> findAll();

    List<TaskBO> findAllByStatus(String taskStatus);

    TaskBO findById(Long id);

    TaskBO findByUUID(String uuid);

    TaskBO add(TaskBO taskDTO);

    TaskBO modify(String uuid, TaskBO taskDTO);

    boolean delete(String uuid);
}
