package com.example.demo230123.controller;

import com.example.demo230123.dto.ResponseDTO;
import com.example.demo230123.dto.UserDTO;
import com.example.demo230123.model.UserEntity;
import com.example.demo230123.security.TokenProvider;
import com.example.demo230123.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            //요청을 이용해 저장할 사용자 만들기
            UserEntity user = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            // 서비스를 이용해 리포지토리에 사용자 저장
            UserEntity registeredUser = userService.create(user);

            UserDTO reponseUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(reponseUserDTO);
        }catch (Exception e){
            // 사용자 정보는 항상 하나이므로 리스트로 만들어야 하는
            // ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){

        UserEntity user= userService.getByCredentials(userDTO.getEmail(),userDTO.getPassword());

        if(user != null){
            //토큰 생성
            final String token = tokenProvider.create(user);

            final UserDTO responseUserDTO =
                    UserDTO.builder()
                            .email(user.getEmail())
                            .id(user.getId())
                            .token(token)
                            .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder().error("Login failed").build();
            return  ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
