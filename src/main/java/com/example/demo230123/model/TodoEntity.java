package com.example.demo230123.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo")
public class TodoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동 생성 GenericGenerator 정의된 이름으로
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;

    private String userId;
    private String title;
    private boolean done;

}
