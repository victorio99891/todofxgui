package pl.wiktor.todosgui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wiktor.todosgui.controllers.model.TaskDTO;
import pl.wiktor.todosgui.events.StageInitializer;
import pl.wiktor.todosgui.events.enums.AvailableView;
import pl.wiktor.todosgui.mapper.TaskMapper;
import pl.wiktor.todosgui.service.TaskService;
import pl.wiktor.todosgui.state.AppStateStorage;

import java.util.Arrays;

@Slf4j
@Component
class TaskModalController {

    @Autowired
    TaskService taskService;

    @Autowired
    StageInitializer stageInitializer;

    private TaskDTO selectedTaskDTO = null;

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
        AppStateStorage.setSelectedTaskDTO(null);
        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.MAIN);
    }

    @FXML
    void taskDelete_Action(ActionEvent event) {
        if (selectedTaskDTO != null) {
            final boolean deleted = taskService.delete(selectedTaskDTO.getUUID());
            if (deleted) {
                stageInitializer.publishStageReadyEvent_SameStage(AvailableView.MAIN);
            }
        }
    }

    @FXML
    void taskSave_Action(ActionEvent event) {
        boolean valid = validateData();
        TaskDTO response = null;

        if (valid) {
            if (selectedTaskDTO == null) {
                log.info("[taskSave_Action] Create new task!");
                TaskDTO taskDTO = TaskDTO.builder()
                        .title(this.taskTitleTxtFd.getText())
                        .details(this.taskDetailsTxtFd.getText())
                        .taskStatus(this.taskStatusChBox.getValue())
                        .build();
                response = TaskMapper.fromBOtoDTO(taskService.add(TaskMapper.fromDTOtoBO(taskDTO)));
            } else {
                log.info("[taskSave_Action] Modify existing task!");
                selectedTaskDTO.setTitle(this.taskTitleTxtFd.getText());
                selectedTaskDTO.setDetails(this.taskDetailsTxtFd.getText());
                selectedTaskDTO.setTaskStatus(this.taskStatusChBox.getValue());
                response = TaskMapper.fromBOtoDTO(taskService.modify(selectedTaskDTO.getUUID(), TaskMapper.fromDTOtoBO(selectedTaskDTO)));
            }
            if (response != null) {
                AppStateStorage.setSelectedTaskDTO(null);
                stageInitializer.publishStageReadyEvent_SameStage(AvailableView.MAIN);
            }
        }
    }

    @FXML
    public void initialize() {
        this.taskStatusChBox.setItems(FXCollections.observableList(Arrays.asList("TODO", "DONE")));
        selectedTaskDTO = AppStateStorage.getSelectedTaskDTO();
        if (selectedTaskDTO == null) {
            this.taskDeleteBtn.setDisable(true);
            this.taskStatusChBox.setValue("TODO");
            this.taskStatusChBox.setDisable(true);
        } else {
            this.taskTitleTxtFd.setText(selectedTaskDTO.getTitle());
            this.taskDetailsTxtFd.setText(selectedTaskDTO.getDetails());
            this.taskStatusChBox.setValue(selectedTaskDTO.getTaskStatus());
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
