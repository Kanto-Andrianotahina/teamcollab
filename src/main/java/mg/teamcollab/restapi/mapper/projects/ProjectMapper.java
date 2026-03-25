package mg.teamcollab.restapi.mapper.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
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
    public ProjectReadDTO toDTO(Project p) {
        ProjectReadDTO dto = new ProjectReadDTO();
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setOwner(p.getOwnerId());
        dto.setCreatedAt(p.getCreatedAt());

        List<ProjectMemberReadDTO> members = projectMemberRepository
                .findByProjectId(p.getId())
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dto.setMembers(members);
        return dto;
    }
}
