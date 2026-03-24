package mg.teamcollab.restapi.controller.projectmembers;

import jakarta.validation.Valid;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberCreateDTO;
import mg.teamcollab.restapi.dto.projectmembers.ProjectMemberReadDTO;
import mg.teamcollab.restapi.service.projectmembers.ProjectMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project-member")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @PostMapping
    public ResponseEntity<ProjectMemberReadDTO> addMember(@Valid @RequestBody ProjectMemberCreateDTO dto) throws Exception {
        return ResponseEntity.ok(projectMemberService.addMember(dto));
    }
}