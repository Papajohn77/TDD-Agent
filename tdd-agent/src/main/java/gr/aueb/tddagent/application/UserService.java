package gr.aueb.tddagent.application;

import gr.aueb.tddagent.api.schemas.request.LoginRequestDTO;
import gr.aueb.tddagent.api.schemas.request.RegisterRequestDTO;
import gr.aueb.tddagent.api.schemas.response.AuthResponseDTO;
import gr.aueb.tddagent.domain.User;
import gr.aueb.tddagent.exception.custom.AlreadyExistsException;
import gr.aueb.tddagent.exception.custom.NotFoundException;
import gr.aueb.tddagent.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getEmail());
        
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotFoundException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getId(), user.getEmail());
        return new AuthResponseDTO(token);
    }

    public User getUserById(java.util.UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}