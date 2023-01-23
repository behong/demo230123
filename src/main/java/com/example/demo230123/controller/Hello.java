package com.example.demo230123.controller;

import com.example.demo230123.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Hello {

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerRequestBody(){
        List<String> list = new ArrayList<String>();
        list.add("hello world I'm ResponseDTO");
        ResponseDTO<String> res = ResponseDTO.<String>builder().data(list).build();
        return res;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerRequestEnitity(){
        List<String> list = new ArrayList<String>();
        list.add("hello world I'm ResponseEntity And you got 400 !");
        ResponseDTO<String> res = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(res);
    }
}
