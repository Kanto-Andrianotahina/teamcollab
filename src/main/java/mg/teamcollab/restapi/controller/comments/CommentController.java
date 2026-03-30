package mg.teamcollab.restapi.controller.comments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.comments.CommentCreateDTO;
import mg.teamcollab.restapi.dto.comments.CommentResponseDTO;
import mg.teamcollab.restapi.service.comments.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable Long taskId,
            @Valid @RequestBody CommentCreateDTO dto) throws Exception {
        return ResponseEntity.ok(commentService.addComment(taskId, dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long taskId) throws Exception {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }
}