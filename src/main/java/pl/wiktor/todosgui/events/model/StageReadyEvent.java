package pl.wiktor.todosgui.events.model;

import org.springframework.context.ApplicationEvent;
import pl.wiktor.todosgui.events.model.StageInfo;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(StageInfo stageInfo) {
        super(stageInfo);
    }

    public StageInfo getStageInfo() {
        return ((StageInfo) getSource());
    }

}
