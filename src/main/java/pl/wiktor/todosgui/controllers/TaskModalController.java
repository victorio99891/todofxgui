package pl.wiktor.todosgui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wiktor.todosgui.events.StageInitializer;
import pl.wiktor.todosgui.events.enums.AvailableView;
import pl.wiktor.todosgui.model.Task;
import pl.wiktor.todosgui.service.TaskService;
import pl.wiktor.todosgui.state.AppStateStorage;

import java.util.Arrays;

@Slf4j
@Component
public class TaskModalController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private StageInitializer stageInitializerImpl;

    private Task selectedTask = null;

    @FXML
    private VBox mainStage;

    @FXML
    private TextField taskTitleTxtFd;

    @FXML
    private Label titleErrorLabel;

    @FXML
    private ChoiceBox<String> taskStatusChBox;

    @FXML
    private TextArea taskDetailsTxtFd;

    @FXML
    private Label detailsErrorLabel;

    @FXML
    private Button taskDeleteBtn;

    @FXML
    private Button taskCancelBtn;

    @FXML
    private Button taskSaveBtn;

    @FXML
    void taskCancel_Action(ActionEvent event) {
        AppStateStorage.setSelectedTask(null);
        stageInitializerImpl.publishStageReadyEvent_SameStage(AvailableView.MAIN);
    }

    @FXML
    void taskDelete_Action(ActionEvent event) {
        if (selectedTask != null) {
            final boolean deleted = taskService.delete(selectedTask.getUUID());
            if (deleted) {
                stageInitializerImpl.publishStageReadyEvent_SameStage(AvailableView.MAIN);
            }
        }
    }

    @FXML
    void taskSave_Action(ActionEvent event) {
        boolean valid = validateData();
        Task response = null;

        if (valid) {
            if (selectedTask == null) {
                log.info("[taskSave_Action] Create new task!");
                Task task = Task.builder()
                        .title(this.taskTitleTxtFd.getText())
                        .details(this.taskDetailsTxtFd.getText())
                        .taskStatus(this.taskStatusChBox.getValue())
                        .build();
                response = taskService.add(task);
            } else {
                log.info("[taskSave_Action] Modify existing task!");
                selectedTask.setTitle(this.taskTitleTxtFd.getText());
                selectedTask.setDetails(this.taskDetailsTxtFd.getText());
                selectedTask.setTaskStatus(this.taskStatusChBox.getValue());
                response = taskService.modify(selectedTask.getUUID(), selectedTask);
            }
            if (response != null) {
                AppStateStorage.setSelectedTask(null);
                stageInitializerImpl.publishStageReadyEvent_SameStage(AvailableView.MAIN);
            }
        }
    }

    @FXML
    public void initialize() {
        this.taskStatusChBox.setItems(FXCollections.observableList(Arrays.asList("TODO", "DONE")));
        selectedTask = AppStateStorage.getSelectedTask();
        if (selectedTask == null) {
            this.taskDeleteBtn.setDisable(true);
            this.taskStatusChBox.setValue("TODO");
            this.taskStatusChBox.setDisable(true);
        } else {
            this.taskTitleTxtFd.setText(selectedTask.getTitle());
            this.taskDetailsTxtFd.setText(selectedTask.getDetails());
            this.taskStatusChBox.setValue(selectedTask.getTaskStatus());
        }
    }

    private boolean validateData() {
        if (this.taskTitleTxtFd.getText().length() == 0 || this.taskTitleTxtFd.getText().isEmpty()) {
            this.titleErrorLabel.setText("Title cannot be empty!");
        }

        if (this.taskDetailsTxtFd.getText().length() == 0 || this.taskDetailsTxtFd.getText().isEmpty()) {
            this.detailsErrorLabel.setText("Details cannot be empty!");
        }

        if (this.taskTitleTxtFd.getText().length() != 0 && this.taskDetailsTxtFd.getText().length() != 0) {
            return true;
        }

        return false;
    }

}
