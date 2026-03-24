package mg.teamcollab.restapi.mapper.projects;

import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
import mg.teamcollab.restapi.model.projects.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectReadDTO toDTO(Project p) {
        ProjectReadDTO dto = new ProjectReadDTO();
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setOwner(p.getOwnerId());
        dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }
}
