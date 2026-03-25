package mg.teamcollab.restapi.dto.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDTO{
    @NotBlank(message = "Content required")
    private String content;

    @NotNull(message = "Author required")
    private Long authorId;
}
