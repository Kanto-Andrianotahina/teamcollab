package mg.teamcollab.restapi.dto.comments;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Long id;
    private String content;
    private Long authorId;
    private LocalDateTime createdAt;
}
