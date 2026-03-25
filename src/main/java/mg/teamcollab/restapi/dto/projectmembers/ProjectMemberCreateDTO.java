package mg.teamcollab.restapi.dto.projectmembers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectMemberCreateDTO {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Project id is required")
    private Long projectId;

    @NotBlank(message = "Role is required")
    private String role;

}
