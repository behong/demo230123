package com.example.demo230123.service;

import com.example.demo230123.model.UserEntity;
import com.example.demo230123.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid arguments");
        }

        final String email = userEntity.getEmail();

        if(userRepository.existByEmail(email)){
            log.warn("Email already exists{}" , email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }
}
