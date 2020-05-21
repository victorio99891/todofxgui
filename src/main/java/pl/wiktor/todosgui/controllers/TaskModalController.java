package pl.wiktor.todosgui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.wiktor.todosgui.JavaFxLauncher;
import pl.wiktor.todosgui.events.StageReadyEvent;
import pl.wiktor.todosgui.model.Task;
import pl.wiktor.todosgui.state.AppStateStorage;

@Slf4j
@Component
public class TaskModalController {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @FXML
    private VBox mainStage;

    @FXML
    private TextField taskTitleTxtFd;

    @FXML
    private Label titleErrorLabel;

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
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(JavaFxLauncher.getMainStage(), new ClassPathResource("MainWindow.fxml"))));
    }

    @FXML
    void taskDelete_Action(ActionEvent event) {
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(JavaFxLauncher.getMainStage(), new ClassPathResource("MainWindow.fxml"))));
    }

    @FXML
    void taskSave_Action(ActionEvent event) {
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(JavaFxLauncher.getMainStage(), new ClassPathResource("MainWindow.fxml"))));
    }

    @FXML
    public void initialize() {
        Task task = AppStateStorage.getSelectedTask();
        if (task == null) {
            this.taskDeleteBtn.setDisable(true);
        } else {
            this.taskTitleTxtFd.setText(task.getTitle());
            this.taskDetailsTxtFd.setText(task.getDetails());
        }
    }

}
