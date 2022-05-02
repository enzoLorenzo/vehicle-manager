package pl.loka.vehiclemanager.task.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.UpdateTaskCommand;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.CreateTaskCommand;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task.domain.TaskState;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private TaskUseCase taskService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks() { return taskService.findTasks(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Long id) { return taskService.findTaskById(id); }

    @PostMapping
    public ResponseEntity<?> addTask (@Valid @RequestBody RestTaskCommand command){
        Task newTask = taskService.addTask(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newTask)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@PathVariable Long id, @Valid @RequestBody RestTaskCommand command) {
        taskService.updateTask(command.toUpdateCommand(id));
    }

    @Data
    static class RestTaskCommand{

        @NotBlank
        private String description;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        @NotBlank
        private TaskState taskState;

        CreateTaskCommand toCreateCommand() {
            return new CreateTaskCommand(description, startDate, endDate, taskState);
        }

        UpdateTaskCommand toUpdateCommand(Long id) {
            return new UpdateTaskCommand(id, description, startDate, endDate, taskState);
        }
    }
}
