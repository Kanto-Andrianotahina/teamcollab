package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectAccessService {
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectAccessService(UserRepository userRepository,
                                ProjectMemberRepository projectMemberRepository) {
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));
    }

    public boolean isAdmin(User user) {
        return user.getRole() != null && user.getRole().name().equals("ADMIN");
    }

    public boolean isManager(User user) {
        return user.getRole() != null && user.getRole().name().equals("MANAGER");
    }

    public boolean isMemberOfProject(Long projectId, Long userId) {
        return projectMemberRepository.existsByProjectIdAndUserId(projectId, userId);
    }

    public ProjectMember getMembership(Long projectId, Long userId) {
        return projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this project"));
    }

    public void checkCanViewProject(Long projectId) {
        User currentUser = getCurrentUser();

        if (isAdmin(currentUser)) {
            return;
        }

        if (!isMemberOfProject(projectId, currentUser.getId())) {
            throw new AccessDeniedException("You do not have access to this project");
        }
    }

    public void checkCanManageProject(Long projectId) {
        User currentUser = getCurrentUser();

        if (isAdmin(currentUser)) {
            return;
        }

        ProjectMember membership = getMembership(projectId, currentUser.getId());

        if (membership.getRole() != ProjectMemberRole.LEAD) {
            throw new AccessDeniedException("Only project LEAD can manage this project");
        }
    }

    public void checkCanManageMembers(Long projectId) {
        User currentUser = getCurrentUser();

        if (isAdmin(currentUser)) {
            return;
        }

        ProjectMember membership = getMembership(projectId, currentUser.getId());

        if (membership.getRole() != ProjectMemberRole.LEAD) {
            throw new AccessDeniedException("Only project LEAD can manage members");
        }
    }
    public boolean canManageMembers(Long projectId) {
        try {
            checkCanManageMembers(projectId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
