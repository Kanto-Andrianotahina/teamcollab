package mg.teamcollab.restapi.dto.projects;

import lombok.Getter;
import lombok.Setter;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponseDTO extends RepresentationModel<ProjectResponseDTO> {
    @Setter
    public String name;
    @Setter
    public String description;
    @Setter
    public Long owner;
    @Setter
    public LocalDateTime createdAt;

    @Setter @Getter
    private List<ProjectMemberResponseDTO> members;
}
