package mg.teamcollab.restapi.service.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberCreateDTO;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.mapper.projectmembers.ProjectMemberMapper;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberMapper projectMemberMapper;

    public ProjectMemberService(ProjectMemberRepository projectMemberRepository,
                                UserRepository userRepository,
                                ProjectRepository projectRepository,
                                ProjectMemberMapper projectMemberMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    public ProjectMemberReadDTO addMember(ProjectMemberCreateDTO dto) throws Exception {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));

        projectMemberRepository.findByUserIdAndProjectId(dto.getUserId(), dto.getProjectId())
                .ifPresent(pm -> {
                    throw new RuntimeException("User already member of this project");
                });

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProject(project);
        pm.setRole(dto.getRole());

        return projectMemberMapper.toDTO(projectMemberRepository.save(pm));
    }
}
