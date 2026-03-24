package mg.teamcollab.restapi.dto.projects;

import java.time.LocalDateTime;

public class ProjectReadDTO {
    public String name;
    public String description;
    public Long owner;
    public LocalDateTime createdAt;
}
