package mg.teamcollab.restapi.service.tasks;

import jakarta.transaction.Transactional;
import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.dto.tasks.TaskDetailDTO;
import mg.teamcollab.restapi.dto.tasks.TaskResponseDTO;
import mg.teamcollab.restapi.mapper.tasks.TaskMapper;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public Task createTask(TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload Required");
        }
        Task t = taskMapper.toTask(dto);
        return taskRepository.save(t);
    }

    public List<TaskResponseDTO> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload Required");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task Not Found"));
        task.setTitle(dto.getTitle());
        task.setStatus(dto.getStatus());
        task.setAssignedUser(dto.getUserId());

        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Transactional
    public void deleteTaskByKey(Long id) throws Exception {
        if (id == null) {
            throw  new Exception("Payload required");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("task Not Found"));
        taskRepository.delete(task);
    }

    public TaskDetailDTO findTaskById(Long id) throws Exception {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task not found"));
        return taskMapper.toDetailDTO(task);
    }
}
