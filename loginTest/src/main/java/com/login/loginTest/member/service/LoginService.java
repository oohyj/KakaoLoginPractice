package com.login.loginTest.member.service;

import com.login.loginTest.feign.KakaoTokenClient;
import com.login.loginTest.feign.KakaoUserClient;
import com.login.loginTest.feign.dto.KakaoTokenResponseDto;
import com.login.loginTest.feign.dto.KakaoUserResponseDto;
import com.login.loginTest.member.JWT.JwtUtils;
import com.login.loginTest.member.dto.LoginResponseDto;
import com.login.loginTest.member.entity.Member;
import com.login.loginTest.member.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String authorizeUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String getInfoUri;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String grandType;

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoUserClient kakaoUserClient;
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    public LoginResponseDto authorization(String code){

        //인가 코드로 토큰 발급 받기
        String kakaoAccessToken = getKakaoToken(code);
        //토큰을 보내 사용자 정보 받기
        KakaoUserResponseDto kakaoUserInfo = getKakaUserInfo(kakaoAccessToken);
        // 카카오 회원번호(=member pk)로 가입 여부 확인
        Member member = createOrGetMember(kakaoUserInfo);
        // jwt token 생성
        String userAccessToken = "Bearer "+ jwtUtils.createToken(kakaoUserInfo);
        String userRefreshToken = jwtUtils.createRefreshToken(kakaoUserInfo);

        return LoginResponseDto.builder()
                        .nickname(member.getNickname())
                        .accessToken(userAccessToken)
                         .refreshToken(userRefreshToken)
                         .build();
    }
    private String getKakaoToken(String code) {
        KakaoTokenResponseDto responseDto = kakaoTokenClient.getAccessToken(
              grandType, clientId,redirectUri,code);
        return responseDto.getAccess_token();
    }

    private KakaoUserResponseDto getKakaUserInfo(String kakaoAccessToken) {
        String authorization = "Bearer " + kakaoAccessToken;
        return kakaoUserClient.getUserInfo(authorization);
    }

    private Member createOrGetMember(KakaoUserResponseDto kakaoUserInfo) {
        Optional<Member> optionalMember = memberRepository.findById(kakaoUserInfo.getId());
        // 해당 사용자가 DB에 없는 경우
        if(optionalMember.isEmpty()){
            Long  memberId = kakaoUserInfo.getId();
            String nickname = kakaoUserInfo.getNickname(); // 카카오 사용자 정보에서 닉네임 가져오기
            Member saveMember = new Member(memberId , nickname);
            return memberRepository.save(saveMember);
        }
        return optionalMember.get();
    }
}
