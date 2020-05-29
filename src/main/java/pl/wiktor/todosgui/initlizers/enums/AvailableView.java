package pl.wiktor.todosgui.initlizers.enums;

import lombok.Getter;

@Getter
public enum AvailableView {
    MAIN("MainWindow.fxml"),
    TASK_MODAL("TaskModal.fxml");

    private String name;

    AvailableView(String name) {
        this.name = name;
    }
}
