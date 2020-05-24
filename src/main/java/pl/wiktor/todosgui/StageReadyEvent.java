package pl.wiktor.todosgui;

import org.springframework.context.ApplicationEvent;
import pl.wiktor.todosgui.StageInfo;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(StageInfo stageInfo) {
        super(stageInfo);
    }

    public StageInfo getStageInfo() {
        return ((StageInfo) getSource());
    }

}
