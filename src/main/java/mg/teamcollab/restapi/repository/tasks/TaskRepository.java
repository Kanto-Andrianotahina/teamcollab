package mg.teamcollab.restapi.repository.tasks;

import mg.teamcollab.restapi.model.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
