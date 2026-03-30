package mg.teamcollab.restapi.service.tasks;

import jakarta.transaction.Transactional;
import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.dto.tasks.TaskDetailDTO;
import mg.teamcollab.restapi.dto.tasks.TaskResponseDTO;
import mg.teamcollab.restapi.mapper.tasks.TaskMapper;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import mg.teamcollab.restapi.service.projects.ProjectAccessService;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private  final TaskMapper taskMapper;
    private final ProjectAccessService projectAccessService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper,
                       ProjectAccessService projectAccessService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectAccessService = projectAccessService;
    }

    @Transactional
    public Task createTask(TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload Required");
        }
        projectAccessService.checkCanCreateTask(dto.getProjectId());
        Task t = taskMapper.toTask(dto);
        return taskRepository.save(t);
    }

    public List<TaskResponseDTO> findAllTasks(String status, Long assignedUserId, LocalDate dueBefore) {
        List<Task> tasks;

        if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (assignedUserId != null) {
            tasks = taskRepository.findByAssignedUser(assignedUserId);
        } else if (dueBefore != null) {
            // ✅ Convertir LocalDate → LocalDateTime pour la comparaison
            tasks = taskRepository.findByDueDateBefore(dueBefore.atStartOfDay());
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(task -> {
                    TaskResponseDTO dto = taskMapper.toDTO(task);
                    dto.add(Link.of("/api/tasks/" + dto.getId()).withSelfRel());
                    dto.add(Link.of("/api/tasks/" + dto.getId() + "/comments").withRel("comments"));
                    return dto;
                })
                .toList();
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload Required");
        }
        projectAccessService.checkCanAccessTask(id);
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

        projectAccessService.checkCanDeleteTask(id);
        taskRepository.delete(task);
    }

    public TaskDetailDTO findTaskById(Long id) throws Exception {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new Exception("Task not found"));

        projectAccessService.checkCanAccessTask(id);
        return taskMapper.toDetailDTO(task);
    }
}
