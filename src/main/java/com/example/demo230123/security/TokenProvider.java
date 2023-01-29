package com.example.demo230123.security;

import com.example.demo230123.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    //사용자 정보를 받아 JWT 생성하는일
    private static final String SECRET_KEY ="NMA8JPct";

    public String create(UserEntity userEntity){
        //기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS));
        /*
        {//header
         "aig":"HS512"
        }
        {//payload
          "sub":"40002105013030",
          "iss":"demo app",
          "iat": 1595753564,
          "exp": 1596251314
        }.
        // SECRETE_KEY를 이용해 서명한 부분
            Nn4d1ldlafjdifaldjafdflaj
         */

        //JWT TOKEN 생성
        return Jwts.builder()
                //header에 들어갈 내용 및 서명을 하기 위한 SCRET_KEY
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                //payload에 들어갈 내용
                .setSubject(userEntity.getId()) //sub
                .setIssuer("demo APP") // iss
                .setIssuedAt(new Date()) //iat
                .setExpiration(expiryDate) //exp
                .compact();
    }

    public String validateAndGetUserId(String token){
        // parseClaimJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이롣를 setSignigKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과비교
        // 위조되지 않았다면 페이로드(claims) 리턴, 위조라면 예외
        // 그중 우리는 userId가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
