package mg.teamcollab.restapi.service.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberCreateDTO;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.model.roles.Role;
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

    public ProjectMemberReadDTO addMember(ProjectMemberCreateDTO dto) throws Exception {
        System.out.println(">>> addMember START");
        System.out.println(">>> dto.userId = " + dto.getUserId());
        System.out.println(">>> dto.projectId = " + dto.getProjectId());
        System.out.println(">>> dto.role = " + dto.getRole());

        projectAccessService.checkCanManageMembers(dto.getProjectId());
        System.out.println(">>> checkCanManageMembers OK");

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        System.out.println(">>> user found = " + user.getId());

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
        System.out.println(">>> project found = " + project.getId());

        if (projectMemberRepository.findByUserIdAndProjectId(dto.getUserId(), dto.getProjectId()).isPresent()) {
            throw new Exception("User already member of this project");
        }
        System.out.println(">>> user not already member");

        ProjectMemberRole role = ProjectMemberRole.valueOf(dto.getRole().trim().toUpperCase());
        System.out.println(">>> role parsed = " + role);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProject(project);
        pm.setRole(role);

        ProjectMember saved = projectMemberRepository.save(pm);
        System.out.println(">>> project member saved, id = " + saved.getId());

        return projectMemberMapper.toDTO(saved);
    }
}
