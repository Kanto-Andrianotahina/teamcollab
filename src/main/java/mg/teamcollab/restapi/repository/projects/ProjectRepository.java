package mg.teamcollab.restapi.repository.projects;

import mg.teamcollab.restapi.model.projects.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
