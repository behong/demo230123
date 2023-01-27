package com.example.demo230123.persistence;

import com.example.demo230123.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,String> {

    UserEntity findByEmail(String email);
    Boolean existByEmail(String email);

    UserEntity findByEmailAndPassword(String email,String password);


}
