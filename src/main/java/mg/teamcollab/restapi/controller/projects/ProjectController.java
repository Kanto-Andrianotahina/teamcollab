package mg.teamcollab.restapi.controller.projects;

import mg.teamcollab.restapi.dto.projects.ProjectCreateDTO;
import mg.teamcollab.restapi.dto.projects.ProjectReadDTO;
import mg.teamcollab.restapi.model.projects.Project;
import mg.teamcollab.restapi.service.projects.ProjectService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Project>  createProject(@RequestBody ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload required");
        }
        return ResponseEntity.ok(projectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProjectReadDTO>>getAllProjects() {
        return ResponseEntity.ok(projectService.findProjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectReadDTO> updateProject(@PathVariable long id, @RequestBody ProjectCreateDTO dto) throws Exception {
        if (dto == null) {
            throw new Exception("Payload required");
        }
        return ResponseEntity.ok(projectService.udpateProjectByKey(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable long id) throws Exception {
        projectService.deleteProjectByKey(id);
        return ResponseEntity.ok().build();
    }
}
