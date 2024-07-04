package com.login.loginTest.member.JWT;

import com.login.loginTest.feign.dto.KakaoUserResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtils {

    private Key key;
    private Key rkey;

    @Autowired
    public JwtUtils(@Value("${spring.jwt.secret}")String secretKey ,
                    @Value("${spring.jwt.refresh-secret}")String refreshKey){

        byte[] decodeKey = Decoders.BASE64.decode(secretKey);
        byte[] decodeRKey = Decoders.BASE64.decode(refreshKey);

        key = Keys.hmacShaKeyFor(decodeKey);
        rkey = Keys.hmacShaKeyFor(decodeRKey);
    }

    //access token 생성
    public String createToken(KakaoUserResponseDto dto){
        Claims claims = Jwts.claims();
        claims.put("id" , dto.getId());
        claims.put("nickname" , dto.getNickname());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 *60*2 *1000)) //2시간
                .signWith(key , SignatureAlgorithm.HS256)
                .compact();
    }

    //refresh token 생성
    public String createRefreshToken(KakaoUserResponseDto dto){
        Claims claims = Jwts.claims();
        claims.put("id" , dto.getId());
        claims.put("nickname" , dto.getNickname());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 *60*2 *1000)) //2시간
                .signWith(rkey , SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isExpired(String token){
        return  Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
