package mg.teamcollab.restapi.repository.projectmembers;

import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.model.projectmembers.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProjectId(Long projectId);

    List<ProjectMember> findByUserId(Long userId);

    Optional<ProjectMember> findByUserIdAndProjectId(Long userId, Long projectId);
}
