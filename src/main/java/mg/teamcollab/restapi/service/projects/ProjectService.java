package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.mapper.projects.ProjectMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import mg.teamcollab.restapi.model.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectAccessService projectAccessService;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectMemberMapper projectMemberMapper, ProjectMemberRepository projectMemberRepository, ProjectAccessService projectAccessService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectMemberMapper = projectMemberMapper;
        this.projectMemberRepository = projectMemberRepository;
        this.projectAccessService = projectAccessService;
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

    public List<ProjectReadDTO> findProjects() {
        User currentUser = projectAccessService.getCurrentUser();

        List<Project> projects;

        if (projectAccessService.isAdmin(currentUser)) {
            projects = projectRepository.findAll();
        } else {
            List<ProjectMember> memberships = projectMemberRepository.findByUserId(currentUser.getId());

            projects = memberships.stream()
                    .map(ProjectMember::getProject)
                    .distinct()
                    .collect(Collectors.toList());
        }

        return projects.stream()
                .map(projectMapper::toDTO)
                .toList();
    }

    public ProjectReadDTO udpateProjectByKey(Long id, ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw  new Exception("Payload required");
        }

        projectAccessService.checkCanManageProject(id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project Not Found"));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setOwnerId(dto.getOwner());
        project.setCreatedAt(LocalDateTime.now());
        return projectMapper.toDTO(projectRepository.save(project));

        /* Project updatedProject = projectRepository.save(project);

        ProjectReadDTO dtoResult = projectMapper.toDTO(updatedProject);

        List<ProjectMemberReadDTO> memberDTOs = projectMemberRepository.findByProjectId(id)
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dtoResult.setMembers(memberDTOs); */
    }
    public void deleteProjectByKey(Long id) throws Exception {
        if (id == null) {
            throw  new Exception("Payload required");
        }

        User currentUser = projectAccessService.getCurrentUser();

        if (!projectAccessService.isAdmin(currentUser)) {
            throw new AccessDeniedException("Only ADMIN can delete a project");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project Not Found"));
        projectRepository.delete(project);
    }
    public ProjectReadDTO findProjectById(Long id) throws Exception {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found"));

        // ADMIN ou membre du projet
        projectAccessService.checkCanViewProject(id);

        // mapper projet
        ProjectReadDTO dto = projectMapper.toDTO(project);

        // mapping members

        List<ProjectMemberReadDTO> memberDTOs = projectMemberRepository.findByProjectId(id)
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dto.setMembers(memberDTOs);

        return dto;
    }
}