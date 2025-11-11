package org.example.firstsession.presentasion.rest.dto.user;

import lombok.Getter;
import lombok.Setter;

public class ExternalUserResponse {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String website;

    public ExternalUserResponse(Long id, String name, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }
}
