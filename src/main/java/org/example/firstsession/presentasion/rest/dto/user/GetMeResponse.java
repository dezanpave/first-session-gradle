package org.example.firstsession.presentasion.rest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class GetMeResponse {
    @Getter
    @Setter
    @JsonProperty("user_id")
    private String userId;
    @Getter
    @Setter
    @JsonProperty("name")
    private String name;
    @Getter
    @Setter
    @JsonProperty("email")
    private String email;

    public GetMeResponse(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
