package com.login.loginTest.feign.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserResponseDto {

    private Long id;
    private String nickname;
}
