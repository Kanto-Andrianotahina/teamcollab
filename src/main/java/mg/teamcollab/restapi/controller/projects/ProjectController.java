package mg.teamcollab.restapi.controller.projects;

import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.service.projects.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping
    public Project createProject(@RequestBody ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload requis");
        }
        return projectService.createProject(dto);
    }

    @GetMapping
    public List<ProjectReadDTO> getAllProjects() {
        return projectService.findProjects();
    }
}
