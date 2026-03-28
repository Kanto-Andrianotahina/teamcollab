package mg.teamcollab.restapi.controller.tasks;

import jakarta.validation.Valid;
import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.dto.tasks.TaskDetailDTO;
import mg.teamcollab.restapi.dto.tasks.TaskResponseDTO;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.service.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody TaskCreateDTO dto) throws Exception {
        if (dto == null)
            throw new Exception("Payload required");

        return taskService.createTask(dto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<TaskResponseDTO> findAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore) {

        return taskService.findAllTasks(status, assignedUserId, dueBefore);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO updateTask(@PathVariable long id, @Valid @RequestBody TaskCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload required");
        }
        return taskService.updateTask(id,dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable long id) throws Exception {
        taskService.deleteTaskByKey(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TaskDetailDTO> findTaskById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(taskService.findTaskById(id));
    }
}
