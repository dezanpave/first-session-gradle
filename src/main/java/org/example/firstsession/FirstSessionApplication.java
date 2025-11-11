package org.example.firstsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.example.firstsession"})
public class FirstSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSessionApplication.class, args);
    }

}
