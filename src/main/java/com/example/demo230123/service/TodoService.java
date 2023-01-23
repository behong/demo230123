package com.example.demo230123.service;


import com.example.demo230123.model.TodoEntity;
import com.example.demo230123.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;
    public String testService(){
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("first todo ").build();
        //TodoEntity 저장
        repository.save(entity);

        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return  savedEntity.getTitle();
    }
}
