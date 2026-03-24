package mg.teamcollab.restapi.dto.projects;

import lombok.Setter;

import java.time.LocalDateTime;

public class ProjectReadDTO {
    @Setter
    public String name;
    @Setter
    public String description;
    @Setter
    public Long owner;
    @Setter
    public LocalDateTime createdAt;
}
