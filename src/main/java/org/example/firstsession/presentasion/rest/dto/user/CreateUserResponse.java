package org.example.firstsession.presentasion.rest.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class CreateUserResponse {
    @Getter
    @Setter
    private UUID userId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String authToken;

    public CreateUserResponse(UUID userId, String name, String email, String authToken) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authToken = authToken;
    }

}
