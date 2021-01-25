package com.fitness.demo.Controllers;

import com.fitness.demo.POJOs.UserDTO;
import com.fitness.demo.Services.SecurityServices.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public void register(@RequestBody UserDTO user){
        customUserDetailsService.register(user);
    }

    @PostMapping("/login")
    public boolean login(){
        return true;
    }
}
