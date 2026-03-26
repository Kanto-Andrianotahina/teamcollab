package mg.teamcollab.restapi.dto.tasks;

import lombok.Data;

@Data
public class TaskMembersDTO {
    private Long userId;
    private long taskCount;
}