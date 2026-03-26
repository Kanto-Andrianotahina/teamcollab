package mg.teamcollab.restapi.dto.projects;

import lombok.Data;
import mg.teamcollab.restapi.dto.tasks.TaskMembersDTO;

import java.util.List;
import java.util.Map;

@Data
public class ProjectStatisticsDTO {
    private Long projectId;
    private int totalTasks;
    private Map<String, Long> tasksByStatus;
    private int totalMembers;
    private List<TaskMembersDTO> tasksByMember;
}
