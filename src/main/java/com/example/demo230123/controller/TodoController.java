package com.example.demo230123.controller;

import com.example.demo230123.dto.ResponseDTO;
import com.example.demo230123.dto.TodoDTO;
import com.example.demo230123.model.TodoEntity;
import com.example.demo230123.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("test")
    public ResponseEntity<?> testTodo(){
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> res = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(res);
    }
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId ="temporary-user"; //temp userid

        // 서비스의 retrieve
        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);

        // 자바 스트림 이용 리턴된 엔티티 리스트를 TodoDTO 리스트로 반환
        List<TodoDTO> tdos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 변환된 TodoDTO 리스트를 통해 ResponseDTO 를 초기화
        ResponseDTO<TodoDTO> res= ResponseDTO.<TodoDTO>builder().data(tdos).build();

        return ResponseEntity.ok().body(res);

    }
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try{
            String temporaryUserId ="temporary-user"; //temp userid
            
            // TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            // id를 null로 초기화 한다 / 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);
            
            // 임시 사용자 아이디를 설정해 준다.
            // 인증이 없으므로 temporary-user 만 로그인 없이 사용할 수 있는 애플리케이션..
            entity.setUserId(temporaryUserId);
            
            // 서비스를 이용해 TodoEntity 엔티티를 생성한다.
            List<TodoEntity> entities = todoService.create(entity);
            
            // 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 반환한다.
            List<TodoDTO> tdos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            // 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
            ResponseDTO<TodoDTO> res =  ResponseDTO.<TodoDTO>builder().data(tdos).build();

            // ResponseDTO  리턴한다
            return ResponseEntity.ok().body(res);

        }catch (Exception e){
            // 예외가 발생하면 dto 대신 error 메세지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId ="temporary-user"; //temp userid

        // dto 를 entity로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);
        // userId를 temporaryUserId 로 초기화 한다 // 차후 인증 구현
        entity.setUserId(temporaryUserId);

        //서비스를 이용해 entity를 업데이트 한다.
        List<TodoEntity> entities = todoService.update(entity);

        //자바 스트럼 이용 리턴된 리스트를 TodoDTO로 변환
        List<TodoDTO> dtos= entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //변환된 TodoDTO 리스트를 통해 ResponseDTO를 초기화 한다.
        ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(res);

    }
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
        try {

            String temporaryUserId ="temporary-user"; //temp userid

            // TodoEntity 로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            // 임시 아이디 설정
            entity.setUserId(temporaryUserId);
            // 서비시를 이용해 entity 를 삭제한다
            List<TodoEntity> entities = todoService.delete(entity);

            //스트림 이용해 리턴된 엔티티를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 변환된 TodoDTO 리스트 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(res);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(res);
        }
    }
}
