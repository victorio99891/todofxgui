package com.mechanitis.demo.stockui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class MainStage extends Application {
    private ConfigurableApplicationContext applicationContext;

    private Resource resource = new ClassPathResource("MainWindow.fxml");

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(StockUiApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(stage, resource)));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

}
