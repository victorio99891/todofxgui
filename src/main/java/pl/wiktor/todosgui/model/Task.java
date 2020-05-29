package pl.wiktor.todosgui.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    private Long _taskId;

    private String UUID;

    private String title;

    private String details;

    private String taskStatus;

    private LocalDateTime creationTime;

    private LocalDateTime statusChangeTime;
}
