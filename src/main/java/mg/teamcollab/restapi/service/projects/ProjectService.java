package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw  new Exception("Payload Requis");
        }
        Project p = new Project();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setOwnerId(dto.getOwner());
        p.setCreatedAt(LocalDateTime.now());
        return projectRepository.save(p);
    }
    public List<Project> findProjects() {
        return projectRepository.findAll();
    }
}