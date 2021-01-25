package com.fitness.demo.Services.SecurityServices;

import com.fitness.demo.Entities.User;
import com.fitness.demo.Exceptions.NoAuthenticationFoundException;
import com.fitness.demo.POJOs.UserDTO;
import com.fitness.demo.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);


        if (user == null){
            throw new UsernameNotFoundException("User with emaiL: " + email + " was not found!");
        }

        return user;
    }

    public User getAuthenticatedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }
        else {
            throw new NoAuthenticationFoundException();
        }
    }

    public User register(UserDTO userDTO) {
        User newUser = new User();
        newUser.setEmail(userDTO.email);
        newUser.setPassword(bCryptPasswordEncoder.encode(userDTO.password));
        newUser.setName(userDTO.name);
        newUser.setLastName(userDTO.lastName);
        newUser.setWeight(userDTO.weight);
        newUser.setHeight(userDTO.height);
        newUser.setBodyFat(userDTO.bodyFat);
        newUser.setAuthRole("ROLE_USER");
        userRepository.save(newUser);

        return newUser;
    }
    public User updateInfo(String name, String surname, MultipartFile image){
        User user = getAuthenticatedUser();
        if (name != null){
            user.setName(name);
        }
        if (surname != null){
            user.setLastName(surname);
        }
        if (image != null){
            try {
                user.setProfile_picture(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return userRepository.save(user);

    }

}
