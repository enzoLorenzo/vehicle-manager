package pl.loka.vehiclemanager.task_rating.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.loka.vehiclemanager.common.Utils;
import pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase;
import pl.loka.vehiclemanager.task_rating.domain.TaskRating;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static pl.loka.vehiclemanager.task_rating.application.port.TaskRatingUseCase.*;

@RestController
@RequestMapping("/repair_rating")
@AllArgsConstructor
public class TaskRatingController {
    
    private TaskRatingUseCase taskRatingService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskRating getTaskRatingById(@NotNull @PathVariable Long id) { return taskRatingService.findTaskRatingById(id); }

    @PostMapping
    public ResponseEntity<?> addTaskRating (@Valid @RequestBody TaskRatingController.RestTaskRatingCommand command){
        TaskRating newTaskRating = taskRatingService.addTaskRating(command.toCreateCommand());
        return ResponseEntity.created(Utils.createUri(newTaskRating)).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTaskRating(@NotNull @PathVariable Long id, @Valid @RequestBody TaskRatingController.RestTaskRatingCommand command) {
        taskRatingService.updateTaskRating(command.toUpdateCommand(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskRating(@NotNull @PathVariable Long id) {
        taskRatingService.deleteTaskRating(id);
    }

    @Data
    static class RestTaskRatingCommand {

        @NotBlank
        private int rating;

        private String comment;

        private Long taskId;

        CreateTaskRatingCommand toCreateCommand() {
            return new CreateTaskRatingCommand(rating, comment, taskId);
        }

        UpdateTaskRatingCommand toUpdateCommand(Long id) {
            return new UpdateTaskRatingCommand(id, rating, comment, taskId);
        }
    }
}
