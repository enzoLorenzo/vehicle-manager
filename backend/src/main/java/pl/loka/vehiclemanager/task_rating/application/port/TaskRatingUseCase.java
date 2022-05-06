package pl.loka.vehiclemanager.task_rating.application.port;


import pl.loka.vehiclemanager.task_rating.domain.TaskRating;

import java.util.List;

public interface TaskRatingUseCase {

    TaskRating findTaskRatingByTaskId(Long task);

    TaskRating findTaskRatingById(Long id);

    TaskRating addTaskRating(CreateTaskRatingCommand command);

    void updateTaskRating(UpdateTaskRatingCommand command);

    void deleteTaskRating(Long id);

    record CreateTaskRatingCommand(int rating, String comment, Long taskId){

    }

    record UpdateTaskRatingCommand(Long id, int rating, String comment, Long taskId){

    }
}
