package mg.teamcollab.restapi.service.comments;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.comments.CommentCreateDTO;
import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.mapper.comments.CommentMapper;
import mg.teamcollab.restapi.model.comments.Comment;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.comments.CommentRepository;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    public CommentResponseDTO addComment(Long taskId, CommentCreateDTO dto) throws Exception {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Task not found"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthorId(dto.getAuthorId());
        comment.setTask(task);

        return commentMapper.toDTO(commentRepository.save(comment));
    }

    public List<CommentResponseDTO> getCommentsByTask(Long taskId) {
        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(commentMapper::toDTO)
                .toList();
    }
}