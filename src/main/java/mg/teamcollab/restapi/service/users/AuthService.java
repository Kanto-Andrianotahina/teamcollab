package mg.teamcollab.restapi.service.users;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.users.LoginRequestDTO;
import mg.teamcollab.restapi.exception.BadRequestException;
import mg.teamcollab.restapi.repository.users.UserRepository;
import mg.teamcollab.restapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Map<String, String> login(LoginRequestDTO request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Email ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());

        return Map.of(
                "token", token,
                "email", user.getEmail(),
                "role",  user.getRole().name()
        );
    }
}
