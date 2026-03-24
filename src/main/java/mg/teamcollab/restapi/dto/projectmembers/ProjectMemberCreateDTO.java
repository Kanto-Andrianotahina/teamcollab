package mg.teamcollab.restapi.dto.projectmembers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProjectMemberCreateDTO {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Project id is required")
    private Long projectId;

    @NotBlank(message = "Role is required")
    private String role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
