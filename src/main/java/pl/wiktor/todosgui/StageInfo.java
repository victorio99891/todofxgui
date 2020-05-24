package pl.wiktor.todosgui;

import javafx.stage.Stage;
import org.springframework.core.io.Resource;

public class StageInfo {
    private Stage stage;
    private Resource fxml;

    public StageInfo(Stage stage, Resource fxml) {
        this.stage = stage;
        this.fxml = fxml;
    }

    public Stage getStage() {
        return stage;
    }

    public Resource getFxml() {
        return fxml;
    }
}
