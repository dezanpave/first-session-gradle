package org.example.firstsession.presentasion.rest.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.example.firstsession.presentasion.rest.dto.user.GetMeResponse;
import org.example.firstsession.usecase.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService ;

    @GetMapping("/me")
    @RateLimiter(name = "userApi")
    public ResponseEntity<GetMeResponse> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        String userId = (String) details.get("userId");
        GetMeResponse response = userService.getMe(UUID.fromString(userId));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
