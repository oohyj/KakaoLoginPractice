package com.login.loginTest.feign;

import com.login.loginTest.feign.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 카카오 서버에 http 요청을 해서 토큰 가져오기 :
@FeignClient(name = "KakaoToken" , url = "https://kauth.kakao.com/oauth/token")
public interface KakaoTokenClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponseDto getAccessToken(@RequestParam("grant_type") String grantType,
                                         @RequestParam("client_id") String clientId,
                                         @RequestParam("redirect_uri") String redirectUri,
                                         @RequestParam("code") String code
                                        );
}
