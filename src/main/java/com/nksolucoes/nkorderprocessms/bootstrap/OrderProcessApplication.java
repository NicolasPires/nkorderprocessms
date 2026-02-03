package com.nksolucoes.nkorderprocessms.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nksolucoes.nkorderprocessms")
public class OrderProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderProcessApplication.class, args);
    }
}
