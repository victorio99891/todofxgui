package pl.wiktor.todosgui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoGuiApplication {

    public static void main(String[] args) {
        Application.launch(JavaFxLauncher.class, args);
    }

    //TODO:
    // - error popup window in case of api errors
    // - File menu operations
    // - CRUD actions with API

}
