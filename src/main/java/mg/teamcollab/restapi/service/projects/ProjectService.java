package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectResponseDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.mapper.projects.ProjectMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectMemberMapper projectMemberMapper, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectMemberMapper = projectMemberMapper;
        this.projectMemberRepository = projectMemberRepository;
    }

    public Project createProject(ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw  new Exception("Payload required");
        }
        Project p = new Project();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setOwnerId(dto.getOwner());
        p.setCreatedAt(LocalDateTime.now());
        return projectRepository.save(p);
    }
    public List<ProjectResponseDTO> findProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDTO)
                .toList();
    }
    public ProjectResponseDTO udpateProjectByKey(Long id, ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw  new Exception("Payload required");
        }
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project Not Found"));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwnerId(dto.getOwner());
        project.setCreatedAt(LocalDateTime.now());
        return projectMapper.toDTO(projectRepository.save(project));
    }
    public void deleteProjectByKey(Long id) throws Exception {
        if (id == null) {
            throw  new Exception("Payload required");
        }
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project Not Found"));
        projectRepository.delete(project);
    }
    public ProjectResponseDTO findProjectById(Long id) throws Exception {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found"));

        // mapper projet
        ProjectResponseDTO dto = projectMapper.toDTO(project);

        //  get members
        List<ProjectMember> members = projectMemberRepository.findByProjectId(id);

        // mapping members

        List<ProjectMemberResponseDTO> memberDTOs = projectMemberRepository.findByProjectId(id)
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dto.setMembers(memberDTOs);

        return dto;
    }
}