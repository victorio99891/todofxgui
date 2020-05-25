package pl.wiktor.todosgui.controllers;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Slf4j
@Component
class MainStageController {

    @Autowired
    private StageInitializer stageInitializer;

    @Autowired
    private TaskService taskService;

    @FXML
    private VBox mainStage;

    @FXML
    private Button createTaskBtn;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem syncMenuItem;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private TableView<TaskDTO> todoTableView;

    @FXML
    private TableColumn<TaskDTO, String> todoTableColumn;

    @FXML
    private TableView<TaskDTO> doneTableView;

    @FXML
    private TableColumn<TaskDTO, String> doneTableColumn;


    @FXML
    public void initialize() {
        fillTaskTables();
        registerClickableTasks();
        registerFileMenuEventHandlers();
    }


    @PostConstruct
    public void init() {

    }

    @FXML
    void createTaskBtn_Click(MouseEvent event) {
        AppStateStorage.setSelectedTaskDTO(null);
        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.TASK_MODAL);
    }

    private void registerFileMenuEventHandlers() {
        this.exitMenuItem.addEventHandler(ActionEvent.ACTION, (event) -> {
            Platform.exit();
        });

        this.syncMenuItem.addEventHandler(ActionEvent.ACTION, (event) -> {
            fillTaskTables();
        });
    }

    private void fillTaskTables() {
        AppStateStorage.setTaskDTOS(
                taskService.findAll()
                        .stream()
                        .map(TaskMapper::fromBOtoDTO)
                        .collect(Collectors.toList()));

        todoTableColumn.setCellValueFactory(data -> {
            StringProperty sp = new SimpleStringProperty();
            sp.setValue(String.valueOf(data.getValue().getTitle()));
            return sp;
        });
        todoTableView.setItems(FXCollections.observableArrayList(AppStateStorage.getToDoTasks()));
        doneTableColumn.setCellValueFactory(data -> {
            StringProperty sp = new SimpleStringProperty();
            sp.setValue(String.valueOf(data.getValue().getTitle()));
            return sp;
        });
        doneTableView.setItems(FXCollections.observableArrayList(AppStateStorage.getDoneTasks()));
    }

    private void registerClickableTasks() {
        this.todoTableView.setRowFactory(x -> {
            TableRow<TaskDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TaskDTO item = row.getItem();
                    log.info(item.toString());
                    if (item != null) {
                        AppStateStorage.setSelectedTaskDTO(item);
                        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.TASK_MODAL);
                    }
                }
            });
            return row;
        });
        this.doneTableView.setRowFactory(x -> {
            TableRow<TaskDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TaskDTO item = row.getItem();
                    log.info(item.toString());
                    if (item != null) {
                        AppStateStorage.setSelectedTaskDTO(item);
                        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.TASK_MODAL);
                    }
                }
            });
            return row;
        });
    }
}
