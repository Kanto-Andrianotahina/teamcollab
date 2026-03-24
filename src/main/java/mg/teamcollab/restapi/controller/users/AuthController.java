package mg.teamcollab.restapi.controller.users;

import lombok.RequiredArgsConstructor;
import mg.teamcollab.restapi.dto.users.LoginRequestDTO;
import mg.teamcollab.restapi.dto.users.RegisterRequestDTO;
import mg.teamcollab.restapi.dto.users.UserResponseDTO;
import mg.teamcollab.restapi.service.users.AuthService;
import mg.teamcollab.restapi.service.users.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
