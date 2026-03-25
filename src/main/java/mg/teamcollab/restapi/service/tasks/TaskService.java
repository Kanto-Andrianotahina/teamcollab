package mg.teamcollab.restapi.service.tasks;

import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.mapper.tasks.TaskMapper;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task createTask(TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload Required");
        }
        Task t = taskMapper.toTask(dto);
        return taskRepository.save(t);
    }
}
