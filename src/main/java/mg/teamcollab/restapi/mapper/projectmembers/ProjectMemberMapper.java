package mg.teamcollab.restapi.mapper.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {
    public ProjectMemberResponseDTO toDTO(ProjectMember pm) {
        ProjectMemberResponseDTO dto = new ProjectMemberResponseDTO();
        dto.setId(pm.getId());
        dto.setUserName(pm.getUser().getUsername());
        dto.setRole(pm.getRole());
        dto.setJoinedAt(pm.getJoinedAt());
        return dto;
    }
}
