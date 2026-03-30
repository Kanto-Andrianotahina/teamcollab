package mg.teamcollab.restapi.service.comments;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.comments.CommentCreateDTO;
import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.exception.NotFoundException;
import mg.teamcollab.restapi.mapper.comments.CommentMapper;
import mg.teamcollab.restapi.model.comments.Comment;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.comments.CommentRepository;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import mg.teamcollab.restapi.service.projects.ProjectAccessService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;
    private final ProjectAccessService projectAccessService;

    public CommentResponseDTO addComment(Long taskId, CommentCreateDTO dto) throws Exception {
        projectAccessService.checkCanCommentTask(taskId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche introuvable : " + taskId));

        User currentUser = projectAccessService.getCurrentUser();

        Comment comment = new Comment();
        comment.setAuthorId(currentUser.getId());
        comment.setContent(dto.getContent());
        comment.setTask(task);

        return commentMapper.toDTO(commentRepository.save(comment));
    }

    public List<CommentResponseDTO> getCommentsByTask(Long taskId) throws Exception {
        projectAccessService.checkCanCommentTask(taskId);

        // Vérifier que la tâche existe
        taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tâche introuvable : " + taskId));

        return commentRepository.findByTaskId(taskId)
                .stream()
                .map(commentMapper::toDTO)
                .toList();
    }
}