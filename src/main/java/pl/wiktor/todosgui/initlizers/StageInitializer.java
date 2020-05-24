package pl.wiktor.todosgui.initlizers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import pl.wiktor.todosgui.JavaFxLauncher;
import pl.wiktor.todosgui.events.StageReadyEvent;
import pl.wiktor.todosgui.events.model.StageInfo;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private String applicationTitle;
    private ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
                            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    public void publishStageReadyEvent_SameStage(AvailableView view) {
        applicationContext.publishEvent(new StageReadyEvent(new StageInfo(JavaFxLauncher.getMainStage(), new ClassPathResource(view.getName()))));
    }

    public void publishStageReadyEvent_NewStage(AvailableView view) {
        applicationContext.publishEvent(new StageReadyEvent(new StageInfo(JavaFxLauncher.getNewStage(), new ClassPathResource(view.getName()))));
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(event.getStageInfo().getFxml().getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStageInfo().getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle(applicationTitle);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}


