package mg.teamcollab.restapi.dto.projectmembers;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ProjectMemberReadDTO {
    @Setter @Getter
    private Long id;
    @Setter @Getter
    private String userName;
    @Setter @Getter
    private String role;
    @Setter @Getter
    private LocalDateTime joinedAt;

}
