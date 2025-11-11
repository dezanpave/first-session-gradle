package org.example.firstsession.presentasion.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class LoginRequest {
    @JsonProperty("email")
    @Getter
    @Setter
    private String email;

    @JsonProperty("password")
    @Getter
    @Setter
    private String password;
}
