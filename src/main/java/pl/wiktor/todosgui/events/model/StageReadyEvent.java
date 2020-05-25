package pl.wiktor.todosgui.events.model;

import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(StageInfo stageInfo) {
        super(stageInfo);
    }

    public StageInfo getStageInfo() {
        return ((StageInfo) getSource());
    }

}
