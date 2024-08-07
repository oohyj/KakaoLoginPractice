package com.login.loginTest.feign;

import com.login.loginTest.feign.dto.KakaoUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoUser" , url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoUserClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoUserResponseDto getUserInfo(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    );
}
