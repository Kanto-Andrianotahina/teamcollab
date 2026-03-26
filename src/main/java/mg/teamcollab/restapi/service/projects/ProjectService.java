package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectStatisticsDTO;
import mg.teamcollab.restapi.dto.tasks.TaskMembersDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.mapper.projects.ProjectMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import org.springframework.security.access.AccessDeniedException;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import org.springframework.stereotype.Service;
import mg.teamcollab.restapi.model.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectAccessService projectAccessService;
    private final TaskRepository taskRepository;

public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, ProjectMemberMapper projectMemberMapper, ProjectMemberRepository projectMemberRepository, TaskRepository taskRepository,ProjectAccessService projectAccessService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectMemberMapper = projectMemberMapper;
        this.projectMemberRepository = projectMemberRepository;
        this.projectAccessService = projectAccessService;
        this.taskRepository = taskRepository;
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
    public ProjectResponseDTO udpateProjectByKey(Long id, ProjectCreateDTO dto) throws Exception {
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
    public ProjectResponseDTO findProjectById(Long id) throws Exception {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new Exception("Project not found"));

        // ADMIN ou membre du projet
        projectAccessService.checkCanViewProject(id);

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

    public ProjectStatisticsDTO getStatistics(Long projectId) throws Exception {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found"));

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        List<ProjectMember> members = projectMemberRepository.findByProjectId(projectId);

        Map<String, Long> tasksByStatus = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        List<TaskMembersDTO> tasksByMember = members.stream()
                .map(member -> {
                    long count = tasks.stream()
                            .filter(t -> t.getAssignedUser() != null &&
                                    t.getAssignedUser().equals(member.getUser().getId()))
                            .count();
                    TaskMembersDTO dto = new TaskMembersDTO();
                    dto.setUserId(member.getUser().getId());
                    dto.setTaskCount(count);
                    return dto;
                })
                .toList();

        ProjectStatisticsDTO stats = new ProjectStatisticsDTO();
        stats.setProjectId(projectId);
        stats.setTotalTasks(tasks.size());
        stats.setTasksByStatus(tasksByStatus);
        stats.setTotalMembers(members.size());
        stats.setTasksByMember(tasksByMember);

        return stats;
    }
}