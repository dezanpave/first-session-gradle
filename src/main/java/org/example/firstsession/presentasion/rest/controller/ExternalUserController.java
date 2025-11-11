package org.example.firstsession.presentasion.rest.controller;

import org.example.firstsession.presentasion.rest.dto.user.ExternalUserResponse;
import org.example.firstsession.usecase.ExternalUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external-users")
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    public ExternalUserController(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @GetMapping
    public ResponseEntity<List<ExternalUserResponse>> getExternalUsers() {
        List<ExternalUserResponse> users = externalUserService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}