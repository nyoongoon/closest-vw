package com.example.closestv2.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberInfoTest {

    private final String ANY_USER_EMAIL = "abc@naver.com";
    private final String ANY_PASSWORD = "Abc1234!!";
    private final String ANY_NICK_NAME = "닉네임";

    private MemberInfo sut;
    private MemberInfo.MemberInfoBuilder builder;

    @BeforeEach
    void setUp() {
        builder = MemberInfo.builder()
                .userEmail(ANY_USER_EMAIL)
                .password(ANY_PASSWORD)
                .nickName(ANY_NICK_NAME);
    }

    @Test
    @DisplayName("MemberInfo 생성 성공 케이스")
    void createMemberInfoSuccessTest() {
        sut = builder.build();
        assertThat(sut.getUserEmail()).isEqualTo(ANY_USER_EMAIL);
        assertThat(sut.getPassword()).isEqualTo(ANY_PASSWORD);
        assertThat(sut.getNickName()).isEqualTo(ANY_NICK_NAME);
    }

    @Test
    @DisplayName("MemberInfo 생성 예외 케이스 - 필수값 null, blank 검증")
    void createMemberInfoNullFailTest() {
        assertThatThrownBy(() -> sut = builder.userEmail(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.userEmail("").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.userEmail(" ").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password("").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password(" ").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.nickName(null).build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.nickName("").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.nickName(" ").build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("MemberInfo 생성 예외 케이스 - 이메일 패턴 검증")
    void createMemberInfoEmailPatternFailTest() {
        assertThatThrownBy(() -> sut = builder.userEmail("sdfv").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.userEmail("as@acc").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.userEmail("abc@acc.a").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.userEmail("abㄹㅇ@acc.a").build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("MemberInfo 생성 예외 케이스 - 비밀번호 패턴 검증")
    void createMemberInfoPasswordPatternFailTest() {
        assertThatThrownBy(() -> sut = builder.password("sdfvsdfv").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password("sdfvsdfv12").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password("Sdfvsdfv").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password("sdfvsdfv!!").build()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sut = builder.password("Aa12!!").build()).isInstanceOf(IllegalArgumentException.class);
    }
}