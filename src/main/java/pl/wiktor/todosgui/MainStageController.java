package pl.wiktor.todosgui;


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

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MainStageController {

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
    private TableView<Task> todoTableView;

    @FXML
    private TableColumn<Task, String> todoTableColumn;

    @FXML
    private TableView<Task> doneTableView;

    @FXML
    private TableColumn<Task, String> doneTableColumn;


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
        AppStateStorage.setSelectedTask(null);
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
        AppStateStorage.setTasks(taskService.findAll());
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
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Task item = row.getItem();
                    log.info(item.toString());
                    if (item != null) {
                        AppStateStorage.setSelectedTask(item);
                        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.TASK_MODAL);
                    }
                }
            });
            return row;
        });
        this.doneTableView.setRowFactory(x -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Task item = row.getItem();
                    log.info(item.toString());
                    if (item != null) {
                        AppStateStorage.setSelectedTask(item);
                        stageInitializer.publishStageReadyEvent_SameStage(AvailableView.TASK_MODAL);
                    }
                }
            });
            return row;
        });
    }
}
