package mg.teamcollab.restapi.dto.users;

import lombok.Data;

@Data
public class UpdatePasswordRequestDTO {
    private String oldPassword;
    private String newPassword;
}
