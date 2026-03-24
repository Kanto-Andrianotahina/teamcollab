package mg.teamcollab.restapi.service.users;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.users.RegisterRequestDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;
import mg.teamcollab.restapi.model.roles.Role;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::from)
                .collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));
        return UserResponseDTO.from(user);
    }

}
