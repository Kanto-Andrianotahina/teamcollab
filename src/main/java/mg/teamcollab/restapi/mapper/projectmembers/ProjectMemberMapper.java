package mg.teamcollab.restapi.mapper.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {
    public ProjectMemberReadDTO toDTO(ProjectMember pm) {
        ProjectMemberReadDTO dto = new ProjectMemberReadDTO();
        dto.setId(pm.getId());
        dto.setUserName(pm.getUser().getUsername());
        dto.setRole(pm.getRole().name());
        dto.setJoinedAt(pm.getJoinedAt());
        return dto;
    }
}
