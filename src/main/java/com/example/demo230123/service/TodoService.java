package com.example.demo230123.service;


import com.example.demo230123.model.TodoEntity;
import com.example.demo230123.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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
    public List<TodoEntity> retrieve(final String userId){
        return  repository.findByUserId(userId);
    }

    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);

        try {
            // entity 삭제한다
            repository.delete(entity);
        }catch (Exception e){
            // 예외 발생시 id 로깅
            log.error("error deleting entity", entity.getId(),e);
            // 컨트롤러로 exception 보낸다
            // 내부로직을 캡슐화 하려면 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }
    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);
        //넘겨 받은 entity id를 이용해 TodoEntity 를 가져온다
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        original.ifPresent( todo ->{
            //반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 데이터베이스에 저장한다.
            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> create(final TodoEntity entity){
        //validations
        validate(entity);

        repository.save(entity);
        log.info("Entity id : {} is saved",entity.getId());
        return repository.findByUserId(entity.getUserId());
        //return repository.findByUserid(entity.getUserId());
    }

    private void validate(final  TodoEntity entity){

        if(entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        /*
        if(entity.getId() == null){
            log.warn("Unknown User");
            throw new RuntimeException("Unknown User");
        }
         */

    }
}
