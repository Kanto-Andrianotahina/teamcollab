package mg.teamcollab.restapi.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mg.teamcollab.restapi.model.projectmembesrole.ProjectMemberRole;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must contain at least 4 characters")
    private String password;

    private String role;

}
