package org.example.firstsession.usecase;

import org.example.firstsession.domain.User;
import org.example.firstsession.presentasion.rest.dto.user.GetMeResponse;
import org.example.firstsession.repository.pgsql.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public GetMeResponse getMe(UUID id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new GetMeResponse(user.getUserId().toString(), user.getName(), user.getEmail());

    }
}
