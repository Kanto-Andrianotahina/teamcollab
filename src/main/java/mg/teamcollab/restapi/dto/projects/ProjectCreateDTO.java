package mg.teamcollab.restapi.dto.projects;



public class ProjectCreateDTO {
    public String name;
    public String description;
    public Long owner;


    public String getName() throws Exception {
        if(name==null)
            throw new Exception("Name required");
        return name;
    }
    public String getDescription() throws Exception {
        if(description==null)
            throw new Exception("Description required");
        return description;
    }
    public Long getOwner() throws Exception {
        if(owner==null)
            throw new Exception("Owner required");
        return owner;
    }

}
