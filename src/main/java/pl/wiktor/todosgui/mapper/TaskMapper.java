package pl.wiktor.todosgui.mapper;


import pl.wiktor.todosgui.controllers.model.TaskDTO;
import pl.wiktor.todosgui.service.model.TaskBO;

public interface TaskMapper {


    static TaskDTO fromBOtoDTO(TaskBO taskBO) {
        return TaskDTO.builder()
                ._taskId(taskBO.get_taskId())
                .UUID(taskBO.getUUID())
                .title(taskBO.getTitle())
                .details(taskBO.getDetails())
                .creationTime(taskBO.getCreationTime())
                .statusChangeTime(taskBO.getStatusChangeTime())
                .taskStatus(taskBO.getTaskStatus())
                .build();
    }

    static TaskBO fromDTOtoBO(TaskDTO taskDTO) {
        return TaskBO.builder()
                ._taskId(taskDTO.get_taskId())
                .UUID(taskDTO.getUUID())
                .title(taskDTO.getTitle())
                .details(taskDTO.getDetails())
                .creationTime(taskDTO.getCreationTime())
                .statusChangeTime(taskDTO.getStatusChangeTime())
                .taskStatus(taskDTO.getTaskStatus())
                .build();
    }
}
