package com.login.loginTest.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.login.loginTest.member.dto.LoginResponseDto;
import com.login.loginTest.member.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController{

    private final LoginService loginService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;


    @GetMapping("/login")
    public ResponseEntity<String> loginPage(Model model){
        String oauth2_url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+clientId+"&redirect_uri="+redirectUri;
        return ResponseEntity.ok().body(oauth2_url);
    }

    @GetMapping("/oauth2/kakao")
    public ResponseEntity<LoginResponseDto> kakaoLoginCallback(@RequestParam("code") String code){
        System.out.println("리다이렉트 완료");
        //클라이언트 서버에서 보내온 코드(인가코드)로 사용자 정보 확인
        LoginResponseDto loginResponseDto = loginService.authorization(code);
        return ResponseEntity.ok().body(loginResponseDto);
    }

}
