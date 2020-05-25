package pl.wiktor.todosgui.state;

import pl.wiktor.todosgui.controllers.model.TaskDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AppStateStorage {
    private static List<TaskDTO> taskDTOS = new ArrayList<>();

    private static TaskDTO selectedTaskDTO = null;

    public static TaskDTO getSelectedTaskDTO() {
        return selectedTaskDTO;
    }

    public static void setSelectedTaskDTO(TaskDTO taskDTO) {
        selectedTaskDTO = taskDTO;
    }

    public static List<TaskDTO> getTaskDTOS() {
        return taskDTOS;
    }

    public static void setTaskDTOS(List<TaskDTO> newTaskDTOS) {
        taskDTOS = newTaskDTOS;
    }

    public static List<TaskDTO> getToDoTasks() {
        return taskDTOS.stream()
                .filter(x -> x.getTaskStatus().equals("TODO"))
                .collect(Collectors.toList());
    }

    public static List<TaskDTO> getDoneTasks() {
        return taskDTOS.stream()
                .filter(x -> x.getTaskStatus().equals("DONE"))
                .collect(Collectors.toList());
    }
}
