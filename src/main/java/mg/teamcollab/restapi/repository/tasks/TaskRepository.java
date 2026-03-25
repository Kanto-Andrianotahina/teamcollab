package mg.teamcollab.restapi.repository.tasks;

import mg.teamcollab.restapi.model.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatus(String status);
    List<Task> findByAssignedUser(Long assignedUserId);
    List<Task> findByDueDateBefore(LocalDateTime dueBefore);
}
