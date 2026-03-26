package mg.teamcollab.restapi.mapper.tasks;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.dto.tasks.TaskCreateDTO;
import mg.teamcollab.restapi.dto.tasks.TaskDetailDTO;
import mg.teamcollab.restapi.dto.tasks.TaskResponseDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;
import mg.teamcollab.restapi.mapper.comments.CommentMapper;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.comments.CommentRepository;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public TaskResponseDTO toDTO(Task task) {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setStatus(task.getStatus());
        taskResponseDTO.setAssignedDate(task.getDueDate());
        taskResponseDTO.setUserId(task.getAssignedUser());
        return taskResponseDTO;
    }
    public Task toTask(TaskCreateDTO dto)  {
       Task t = new Task();
       t.setTitle(dto.getTitle());
       t.setStatus(dto.getStatus());
       t.setAssignedUser(dto.getUserId());
       t.setProjectId(dto.getProjectId());
       return t;
    }

    public TaskDetailDTO toDetailDTO(Task task) {
        TaskDetailDTO dto = new TaskDetailDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setStatus(task.getStatus());
        dto.setAssignedDate(task.getDueDate());

        userRepository.findById(task.getAssignedUser()).ifPresent(user -> {
            UserResponseDTO userDTO = new UserResponseDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            dto.setUser(userDTO);
        });

        //
        List<CommentResponseDTO> comments = commentRepository
                .findByTaskId(task.getId())
                .stream()
                .map(commentMapper::toDTO)
                .toList();
        dto.setComments(comments);

        return dto;
    }

}
