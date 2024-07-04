package com.login.loginTest.member.dto;

import com.login.loginTest.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class LoginResponseDto {

    private String nickname;
    private String accessToken;
    private String refreshToken;

}
