package com.example.demo230123.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {

    @Id
    @GeneratedValue(generator = "system-uuid") //id 자동 생성 GenericGenerator 정의된 이름으로
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id;  //사용자 고유 Id
    @Column(nullable = false)
    private String username;   // 사용자 이름
    @Column(nullable = false)
    private String email;    // 사용자 Email, 아이디 같은 기능을 한다.
    @Column(nullable = false)
    private String password; //패스워드
}
