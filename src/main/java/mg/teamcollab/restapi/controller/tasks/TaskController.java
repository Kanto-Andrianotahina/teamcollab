package mg.teamcollab.restapi.controller.tasks;

import jakarta.validation.Valid;
import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.dto.tasks.TaskResponseDTO;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.service.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
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
    public List<TaskResponseDTO> findAllTasks() {
        return taskService.findAllTasks();
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
}
