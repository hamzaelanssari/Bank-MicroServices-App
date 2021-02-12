package org.sid.compteoperationsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class CompteOperationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompteOperationsServiceApplication.class, args);
    }

}
