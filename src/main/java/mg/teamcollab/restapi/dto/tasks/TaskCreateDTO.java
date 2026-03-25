package mg.teamcollab.restapi.dto.tasks;

public class TaskCreateDTO {
    private  String title;
    private  String status;
    private  Long userId;

    public  String getTitle() throws Exception{
        if (title == null) {
            throw new Exception("Title required");
        }
        return title;
    }
    public void setTitle(String title) throws Exception{
        if (title == null) {
            throw new Exception("Title required");
        }
        this.title = title;
    }

    public String getStatus() throws Exception{
        if (status == null) {
            throw new Exception("Status required");
        }
        return status;
    }
    public void setStatus(String status) throws Exception{
        if (status == null) {
            throw new Exception("Status required");
        }
        this.status = status;
    }
    public Long getUserId() throws Exception{
        if (userId == null) {
            throw new Exception("User required");
        }
        return userId;
    }
    public void setUserId(Long userId) throws Exception{
        if (userId == null) {
            throw new Exception("User required");
        }
    }
}
