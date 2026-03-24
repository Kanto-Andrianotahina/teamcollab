package mg.teamcollab.restapi.dto.users;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}