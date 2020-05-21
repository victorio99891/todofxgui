package pl.wiktor.todosgui.state;

import pl.wiktor.todosgui.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppStateStorage {
    private static List<Task> tasks = new ArrayList<>();

    private static Task selectedTask = null;

    private AppStateStorage() {

    }

    public static Task getSelectedTask() {
        return selectedTask;
    }

    public static void setSelectedTask(Task task) {
        selectedTask = task;
    }

    public static List<Task> getTasks() {
        return tasks;
    }

    public static void setTasks(List<Task> ntasks) {
        tasks = ntasks;
    }

    public static List<Task> getToDoTasks() {
        return tasks.stream()
                .filter(x -> x.getTaskStatus().equals("TODO"))
                .collect(Collectors.toList());
    }

    public static List<Task> getDoneTasks() {
        return tasks.stream()
                .filter(x -> x.getTaskStatus().equals("DONE"))
                .collect(Collectors.toList());
    }
}
