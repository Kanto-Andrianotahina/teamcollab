package mg.teamcollab.restapi.mapper.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import org.springframework.stereotype.Component;

@Component
public class ProjectMemberMapper {
    public ProjectMemberReadDTO toDTO(ProjectMember pm) {
        ProjectMemberReadDTO dto = new ProjectMemberReadDTO();
        dto.setId(pm.getId());
        dto.setUserId(pm.getUser().getId());
        dto.setProjectId(pm.getProject().getId());
        dto.setRole(pm.getRole());
        dto.setJoinedAt(pm.getJoinedAt());
        return dto;
    }
}
