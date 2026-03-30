package mg.teamcollab.restapi.service.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberCreateDTO;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberResponseDTO;
import mg.teamcollab.restapi.exception.BadRequestException;
import mg.teamcollab.restapi.exception.NotFoundException;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import mg.teamcollab.restapi.repository.users.UserRepository;
import mg.teamcollab.restapi.service.projects.ProjectAccessService;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;
    private final ProjectAccessService projectAccessService;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                UserRepository userRepository,
                                ProjectRepository projectRepository,
                                ProjectMemberMapper projectMemberMapper,
                                ProjectAccessService projectAccessService) {
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberMapper = projectMemberMapper;
        this.projectAccessService = projectAccessService;
    }

    public ProjectMemberResponseDTO addMember(ProjectMemberCreateDTO dto) {
        projectAccessService.checkCanManageMembers(dto.getProjectId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable : " + dto.getUserId()));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Projet introuvable : " + dto.getProjectId()));

        if (projectMemberRepository.findByUserIdAndProjectId(dto.getUserId(), dto.getProjectId()).isPresent()) {
            throw new BadRequestException("L'utilisateur est déjà membre de ce projet");
        }

        ProjectMemberRole role;
        try {
            role = ProjectMemberRole.valueOf(dto.getRole().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Rôle invalide : " + dto.getRole());
        }

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProject(project);
        pm.setRole(role);

        return projectMemberMapper.toDTO(projectMemberRepository.save(pm));
    }
}