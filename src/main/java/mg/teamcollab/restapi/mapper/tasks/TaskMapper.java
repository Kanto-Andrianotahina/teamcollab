package mg.teamcollab.restapi.mapper.tasks;

import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.model.tasks.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toTask(TaskCreateDTO dto) throws Exception {
        if (dto != null)
            return new Task(dto.getTitle(),dto.getStatus(), dto.getUserId());

        return null;
    }
}
