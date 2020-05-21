package pl.wiktor.todosgui.events;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.io.Resource;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(StageInfo stageInfo) {
        super(stageInfo);
    }

    public StageInfo getStageInfo() {
        return ((StageInfo) getSource());
    }

    public static class StageInfo {
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
}
