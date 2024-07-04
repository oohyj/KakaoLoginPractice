package com.login.loginTest.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public Member(Long memberId , String nickname){
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
