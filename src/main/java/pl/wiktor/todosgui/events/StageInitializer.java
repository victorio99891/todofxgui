package pl.wiktor.todosgui.events;

import org.springframework.stereotype.Component;
import pl.wiktor.todosgui.events.enums.AvailableView;

@Component
public interface StageInitializer {

    void publishStageReadyEvent_SameStage(AvailableView view);
    void publishStageReadyEvent_NewStage(AvailableView view);
}
