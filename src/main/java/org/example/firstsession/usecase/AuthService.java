package org.example.firstsession.usecase;

import org.example.firstsession.domain.User;
import org.example.firstsession.helper.JwtHelper;
import org.example.firstsession.repository.pgsql.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.example.firstsession.presentasion.rest.dto.user.CreateUserRequest;
import org.example.firstsession.presentasion.rest.dto.user.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.firstsession.presentasion.rest.dto.user.LoginRequest;
import org.example.firstsession.presentasion.rest.dto.user.LoginResponse;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtHelper jwtHelper;

    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if (createUserRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }
        if (createUserRequest.getEmail() == null || createUserRequest.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (isUserExists(createUserRequest.getEmail())) {
            throw new IllegalStateException("User with email already exists");
        }

        String password = createUserRequest.getPassword();
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        String encodedPassword = passwordEncoder.encode(password);
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .userId(userId)
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .password(encodedPassword)
                .build();

        User saved = userRepository.save(user);
        String authToken = jwtHelper.generateToken(saved);

        return new CreateUserResponse(saved.getUserId(), saved.getName(), saved.getEmail(), authToken);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new IllegalArgumentException("Request must not be null");
        }
        var userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        User user = userOpt.get();
        boolean isMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!isMatch) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String authToken = jwtHelper.generateToken(user);
        return new LoginResponse(user.getUserId(), user.getName(), user.getEmail(), authToken);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
