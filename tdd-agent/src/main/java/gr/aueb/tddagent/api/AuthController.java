package gr.aueb.tddagent.api;

import gr.aueb.tddagent.api.schemas.request.LoginRequestDTO;
import gr.aueb.tddagent.api.schemas.request.RegisterRequestDTO;
import gr.aueb.tddagent.api.schemas.response.AuthResponseDTO;
import gr.aueb.tddagent.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthResponseDTO response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}