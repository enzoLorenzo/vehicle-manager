package pl.loka.vehiclemanager.task.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.task.application.NewTaskService;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.CreateTaskCommand;
import pl.loka.vehiclemanager.task.application.port.TaskUseCase.UpdateTaskCommand;
import pl.loka.vehiclemanager.task.domain.Task;
import pl.loka.vehiclemanager.task.domain.TaskStatus;
import pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase;
import pl.loka.vehiclemanager.task_rating.domain.TaskRating;
import pl.loka.vehiclemanager.workshop.application.port.WorkshopUseCase;
import pl.loka.vehiclemanager.workshop.domain.Workshop;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import static pl.loka.vehiclemanager.task.application.port.TaskUseCase.UpdateStatusCommand;

@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private TaskUseCase taskService;
    private NewTaskService newTaskService;

    private WorkshopUseCase workshopService;
    private TaskRatingUseCase repairRatingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks() {
        return taskService.findTasks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findTaskById(id);
    }

    @GetMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.OK)
    public TaskRating getRepairRatingByTaskId(@PathVariable Long id) {
        return repairRatingService.findTaskRatingByTaskId(id);
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody RestTaskCommand command) {
        Task newTask = newTaskService.addTask(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newTask)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@PathVariable Long id, @Valid @RequestBody RestTaskCommand command) {
        taskService.updateTask(command.toUpdateCommand(id));
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateTaskStatus(@PathVariable Long id, @RequestBody TaskStatus status) {
        taskService.updateTaskStatus(new UpdateStatusCommand(id, status));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @Data
    static class RestTaskCommand {

        @NotBlank
        private String description;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        @NotBlank
        private TaskStatus taskStatus;

        private Long vehicleId;
        private Long workshopId;

        CreateTaskCommand toCreateCommand() {
            return new CreateTaskCommand(description, startDate, endDate, taskStatus, vehicleId, workshopId);
        }

        UpdateTaskCommand toUpdateCommand(Long id) {
            return new UpdateTaskCommand(id, description, startDate, endDate, taskStatus, vehicleId, workshopId);
        }
    }
}
