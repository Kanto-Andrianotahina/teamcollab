package mg.teamcollab.restapi.mapper.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectResponseDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectMapper {
    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    private ProjectMemberMapper projectMemberMapper;
    public ProjectResponseDTO toDTO(Project p) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setOwner(p.getOwnerId());
        dto.setCreatedAt(p.getCreatedAt());

        List<ProjectMemberResponseDTO> members = projectMemberRepository
                .findByProjectId(p.getId())
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dto.setMembers(members);
        return dto;
    }
}
