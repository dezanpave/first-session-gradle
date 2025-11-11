package org.example.firstsession.presentasion.grpc.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.firstsession.presentasion.grpc.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ServerConfig {
    @Autowired
    private UserHandler userHandler;

    @Bean
    public Server grpcServer() throws IOException {
        return ServerBuilder.forPort(9091)
                .addService(userHandler)
                .build()
                .start();
    }
}
