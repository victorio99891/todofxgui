package pl.wiktor.todosgui.service;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

@Component
public interface ExceptionHandler {
    static void resolve(String exceptionTitle, String exceptionDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unexpected exception occurred:");
        alert.setHeaderText(exceptionTitle);
        alert.setContentText(exceptionDetails);
        alert.showAndWait();
    }
}
