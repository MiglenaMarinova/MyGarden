package com.example.mygarden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyGardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyGardenApplication.class, args);
    }

}
