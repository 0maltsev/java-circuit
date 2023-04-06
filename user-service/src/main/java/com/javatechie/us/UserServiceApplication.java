package com.javatechie.us;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootApplication
@RestController
@RequestMapping("/user-service")
public class UserServiceApplication {

    public static final String USER_SERVICE="userService";



    @GetMapping("from/{firstCurrency}/to/{secondCurrency}")
    @CircuitBreaker(name =USER_SERVICE,fallbackMethod = "getAllAvailableProducts")
    public Map<String, Object> exchange(@PathVariable String firstCurrency, @PathVariable String secondCurrency,
                                        @RequestParam int value) {
        HashMap<String, Object> response = new HashMap<>();
        response.put(secondCurrency,value * 0.013);
        response.put("instanceId", "Producer1");
        return response;
    }


    public String getAllAvailableProducts(Exception e){
        return "This page is under construction";
    }


    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
