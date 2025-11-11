package org.example.firstsession.usecase;

import org.example.firstsession.helper.CircuitBreakerHelper;
import org.example.firstsession.presentasion.rest.dto.user.ExternalUserResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ExternalUserService {
    private final CircuitBreakerHelper circuitBreakerHelper;
    private static final String EXTERNAL_API_URL = "https://jsonplaceholder.typicode.com/users";

    public ExternalUserService(CircuitBreakerHelper circuitBreakerHelper) {
        this.circuitBreakerHelper = circuitBreakerHelper;
    }

    public List<ExternalUserResponse> getAllUsers() {
        ExternalUserResponse[] users = circuitBreakerHelper.callExternalApi(EXTERNAL_API_URL, ExternalUserResponse[].class);
        return users != null ? Arrays.asList(users) : List.of();
    }

}
