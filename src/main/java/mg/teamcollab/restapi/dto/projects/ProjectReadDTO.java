package mg.teamcollab.restapi.dto.projects;

import lombok.Getter;
import lombok.Setter;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectReadDTO extends RepresentationModel<ProjectReadDTO> {
    @Setter
    public String name;
    @Setter
    public String description;
    @Setter
    public Long owner;
    @Setter
    public LocalDateTime createdAt;

    @Setter @Getter
    private List<ProjectMemberReadDTO> members;
}
