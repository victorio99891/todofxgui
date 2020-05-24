package pl.wiktor.todosgui;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

@Component
public interface ExceptionHandler {
    static void resolve(String exceptionTitle, String exceptionDetails) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Unexpected exception");
        alert.setHeaderText(exceptionTitle);
        alert.setContentText(exceptionDetails);
        alert.showAndWait();
    }
}
