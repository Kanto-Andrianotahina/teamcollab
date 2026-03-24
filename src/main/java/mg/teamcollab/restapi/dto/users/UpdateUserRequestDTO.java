package mg.teamcollab.restapi.dto.users;

import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    private String username;
    private String email;

}
