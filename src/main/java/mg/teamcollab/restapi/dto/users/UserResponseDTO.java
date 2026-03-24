package mg.teamcollab.restapi.dto.users;

import lombok.Data;
import mg.teamcollab.restapi.model.users.User;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;

    public static UserResponseDTO from(User user) {
        UserResponseDTO res = new UserResponseDTO();
        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setEmail(user.getEmail());
        res.setRole(user.getRole().name());
        return res;
    }
}
