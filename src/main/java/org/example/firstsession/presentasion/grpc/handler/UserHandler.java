package org.example.firstsession.presentasion.grpc.handler;

import io.grpc.stub.StreamObserver;
import org.example.firstsession.presentasion.rest.dto.user.CreateUserRequest;
import org.example.firstsession.presentasion.rest.dto.user.CreateUserResponse;
import org.example.firstsession.presentasion.rest.dto.user.GetMeResponse;
import org.example.firstsession.proto.*;
import org.example.firstsession.usecase.AuthService;
import org.example.firstsession.usecase.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserHandler extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @Override
    public void getUserDetail(GetUserDetailRequest request, StreamObserver<GetUserDetailResponse> responseObserver) {
        String userId = request.getUserId();
        GetMeResponse response = userService.getMe(UUID.fromString(userId));
        GetUserDetailResponse.Builder builder = GetUserDetailResponse.newBuilder();
        builder.setUserId(response.getUserId().toString());
        builder.setName(response.getName());
        builder.setEmail(response.getEmail());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createNewUser(CreateNewUserRequest request, StreamObserver<CreateNewUserResponse> responseObserver) {
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();

        CreateUserResponse newUser = authService.createUser(new CreateUserRequest(name, email, password));

        CreateNewUserResponse.Builder builder = CreateNewUserResponse.newBuilder();
        builder.setUserId(newUser.toString());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
