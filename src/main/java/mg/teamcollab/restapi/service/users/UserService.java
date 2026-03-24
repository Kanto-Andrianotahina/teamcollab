package mg.teamcollab.restapi.service.users;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.users.RegisterRequestDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;
import mg.teamcollab.restapi.model.roles.Role;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé : " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // sans encodage
                .role(Role.MEMBER)           // par défaut
                .build();

        return UserResponseDTO.from(userRepository.save(user));
    }

}
