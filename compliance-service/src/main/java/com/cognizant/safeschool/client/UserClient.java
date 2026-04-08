package com.cognizant.safeschool.client;

import com.cognizant.safeschool.dto.UserRegistrationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserRegistrationDto getUserById(@PathVariable("id") Long id);
}