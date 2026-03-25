package mg.teamcollab.restapi.dto.tasks;

import lombok.Data;
import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDetailDTO {
    private Long id;
    private String title;
    private String status;
    private LocalDateTime assignedDate;
    private UserResponseDTO user;
    private List<CommentResponseDTO> comments;
}