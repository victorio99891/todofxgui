package pl.wiktor.todosgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import pl.wiktor.todosgui.events.StageReadyEvent;

public class JavaFxLauncher extends Application {
    private static Stage mainStage = null;

    private ConfigurableApplicationContext applicationContext;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Stage getNewStage() {
        return new Stage();
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ToDoGuiApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        applicationContext.publishEvent(new StageReadyEvent(new StageReadyEvent.StageInfo(stage, new ClassPathResource("MainWindow.fxml"))));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

}
