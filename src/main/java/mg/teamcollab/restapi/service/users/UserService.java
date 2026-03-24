package mg.teamcollab.restapi.service.users;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.users.RegisterRequestDTO;
import mg.teamcollab.restapi.dto.users.UpdatePasswordRequestDTO;
import mg.teamcollab.restapi.dto.users.UpdateUserRequestDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;
import mg.teamcollab.restapi.exception.BadRequestException;
import mg.teamcollab.restapi.exception.NotFoundException;
import mg.teamcollab.restapi.model.roles.Role;
import mg.teamcollab.restapi.model.users.User;
import mg.teamcollab.restapi.repository.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email déjà utilisé : " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
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
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable : " + id));
        return UserResponseDTO.from(user);
    }

    public UserResponseDTO update(Long id, UpdateUserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable : " + id));

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null)    user.setEmail(request.getEmail());

        return UserResponseDTO.from(userRepository.save(user));
    }

    public void updatePassword(Long id, UpdatePasswordRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable : " + id));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Ancien mot de passe incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("Le nouveau mot de passe doit être différent de l'ancien");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}