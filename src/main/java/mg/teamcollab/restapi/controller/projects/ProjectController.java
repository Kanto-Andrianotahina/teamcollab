package mg.teamcollab.restapi.controller.projects;

import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.service.projects.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
