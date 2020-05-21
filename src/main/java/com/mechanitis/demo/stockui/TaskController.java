package com.mechanitis.demo.stockui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TaskController {
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private Resource resource = new ClassPathResource("MainWindow.fxml");

    @FXML
    private Button doTaskBtn;

    @FXML
    public void initialize() {

    }

    @FXML
    void doTaskBtn_Click(MouseEvent event) {
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(new Stage(), resource)));
    }
}
