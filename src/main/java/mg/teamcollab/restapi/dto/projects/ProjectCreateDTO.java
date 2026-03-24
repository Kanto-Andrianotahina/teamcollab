package mg.teamcollab.restapi.dto.projects;


import lombok.Getter;

public class ProjectCreateDTO {
    @Getter
    public String name;
    @Getter
    public String description;
    @Getter
    public Long owner;

}
