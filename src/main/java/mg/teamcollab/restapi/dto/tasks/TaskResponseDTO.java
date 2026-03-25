package mg.teamcollab.restapi.dto.tasks;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
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
