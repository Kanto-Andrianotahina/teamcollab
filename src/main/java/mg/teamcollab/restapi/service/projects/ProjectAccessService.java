package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;
import mg.teamcollab.restapi.model.tasks.Task;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.projectmembers.ProjectMemberRepository;
import mg.teamcollab.restapi.repository.tasks.TaskRepository;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectAccessService {
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TaskRepository taskRepository;

    public ProjectAccessService(UserRepository userRepository,
                                ProjectMemberRepository projectMemberRepository,
                                TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.taskRepository = taskRepository;
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

    public void checkCanCreateTask(Long projectId) {
        User currentUser = getCurrentUser();
// ADMIN → OK
        if (isAdmin(currentUser)) {
            return;
        }
        // doit être membre du projet
        ProjectMember membership = projectMemberRepository
                .findByProjectIdAndUserId(projectId, currentUser.getId())
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this project"));

        // seul LEAD
        if (membership.getRole() != ProjectMemberRole.LEAD) {
            throw new AccessDeniedException("Only LEAD can create tasks in this project ");
        }


    }

    public void checkCanDeleteTask(Long taskId) throws Exception {
        User currentUser = getCurrentUser();

        if (isAdmin(currentUser)) {
            return;
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Task not found"));

        Long projectId = task.getProjectId();

        ProjectMember membership = projectMemberRepository
                .findByUserIdAndProjectId(currentUser.getId(), projectId)
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this project"));

        if (membership.getRole() != ProjectMemberRole.LEAD) {
            throw new AccessDeniedException("Only LEAD or ADMIN can delete this task");
        }
    }

    public void checkCanAccessTask(Long taskId) throws Exception {
        User currentUser = getCurrentUser();
        if (isAdmin(currentUser)) {
            return;
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new Exception("Task not found"));

        Long projectId = task.getProjectId();

        boolean isMember = projectMemberRepository
                .existsByUserIdAndProjectId(currentUser.getId(), projectId);

        if (!isMember) {
            throw new AccessDeniedException("You are not a member of this project");
        }
    }

    public void checkCanCommentTask(Long taskId) throws Exception {
        checkCanAccessTask(taskId);
    }
}
