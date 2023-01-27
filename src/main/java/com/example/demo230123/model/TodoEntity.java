package com.example.demo230123.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
