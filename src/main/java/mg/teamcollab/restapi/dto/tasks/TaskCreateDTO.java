package mg.teamcollab.restapi.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskCreateDTO {
    @NotBlank(message = "Title required")
    private  String title;
    @NotBlank(message = "Status required")
    private  String status;
    @NotNull(message = "User required")
    private  Long userId;
    @NotNull(message = "Project required")
    private Long projectId;
}
