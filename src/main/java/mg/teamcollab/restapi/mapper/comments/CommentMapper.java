package mg.teamcollab.restapi.mapper.comments;

import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.model.comments.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponseDTO toDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAuthorId(comment.getAuthorId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}