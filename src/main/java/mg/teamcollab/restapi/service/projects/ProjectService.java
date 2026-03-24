package mg.teamcollab.restapi.service.projects;

import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
import mg.teamcollab.restapi.mapper.projects.ProjectMapper;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.repository.projects.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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
    public List<ProjectReadDTO> findProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDTO)
                .toList();
    }
}