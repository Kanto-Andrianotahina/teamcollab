package mg.teamcollab.restapi.dto.tasks;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO  extends RepresentationModel<TaskResponseDTO> {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String status;
    @Getter @Setter
    private LocalDateTime assignedDate;
    @Getter @Setter
    private Long userId;
}
