package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectResponseDTO;
import mg.teamcollab.restapi.dto.projects.ProjectStatisticsDTO;
import mg.teamcollab.restapi.dto.tasks.TaskMembersDTO;
import mg.teamcollab.restapi.exception.BadRequestException;
import mg.teamcollab.restapi.exception.NotFoundException;
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

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectAccessService projectAccessService;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper,
                          ProjectMemberMapper projectMemberMapper,
                          ProjectMemberRepository projectMemberRepository,
                          TaskRepository taskRepository,
                          ProjectAccessService projectAccessService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.projectMemberMapper = projectMemberMapper;
        this.projectMemberRepository = projectMemberRepository;
        this.projectAccessService = projectAccessService;
        this.taskRepository = taskRepository;
    }

    public Project createProject(ProjectCreateDTO dto) throws Exception{
        if (dto == null) {
            throw new BadRequestException("Payload requis");
        }

        User currentUser = projectAccessService.getCurrentUser();
        Project project = new Project();
        Long owner = currentUser.getId();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        if (dto.getOwner() != null) owner = dto.getOwner();
        project.setOwnerId(owner);
        project.setCreatedAt(LocalDateTime.now());
        return projectRepository.save(project);
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

    public ProjectResponseDTO udpateProjectByKey(Long id, ProjectCreateDTO dto) throws Exception{
        if (dto == null) {
            throw new BadRequestException("Payload requis");
        }

        projectAccessService.checkCanManageProject(id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet introuvable : " + id));

        User currentUser = projectAccessService.getCurrentUser();
        Long owner = currentUser.getId();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        if (dto.getOwner() != null) owner = dto.getOwner();
        project.setOwnerId(owner);
        project.setCreatedAt(LocalDateTime.now());
        return projectMapper.toDTO(projectRepository.save(project));
    }

    public void deleteProjectByKey(Long id) {
        if (id == null) {
            throw new BadRequestException("L'identifiant du projet est requis");
        }

        User currentUser = projectAccessService.getCurrentUser();

        if (!projectAccessService.isAdmin(currentUser)) {
            throw new AccessDeniedException("Seul un ADMIN peut supprimer un projet");
        }

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet introuvable : " + id));

        projectRepository.delete(project);
    }

    public ProjectResponseDTO findProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projet introuvable : " + id));

        projectAccessService.checkCanViewProject(id);

        ProjectResponseDTO dto = projectMapper.toDTO(project);

        List<ProjectMemberResponseDTO> memberDTOs = projectMemberRepository.findByProjectId(id)
                .stream()
                .map(projectMemberMapper::toDTO)
                .toList();

        dto.setMembers(memberDTOs);
        return dto;
    }

    public ProjectStatisticsDTO getStatistics(Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Projet introuvable : " + projectId));

        projectAccessService.checkCanViewProject(projectId);

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