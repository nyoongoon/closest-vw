package com.example.closestv2.infrastructure.rss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BlogAuthenticatorTest {

    @Test
    @DisplayName("memberId와 rssUrl로 캐시된 인증 코드를 생성한다.")
    void createAuthCode(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }
    @Test
    @DisplayName("캐시된 인증 코드의 제한시간은 10분이다.")
    void createAuthCodeLimit10Minute(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("동일한 memberId로 캐시가 요청되면 기존 코드를 엎어쓰고 새로 생성한다.")
    void recreateAuthCodeWithSameMemberId(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("rss가 조회되지 않은 rssUrl을 요청 시 예외가 발생한다.")
    void createAuthCodeWithNotRssUrl(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("")
    void authenticate(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("인증 코드 인증 요청 시 캐시에 존재하지 않는 memberId인 경우 예외가 발생한다.")
    void authenticateWithNotExistsdMemberIdInCache(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("인증 코드 인증 요청 시 Rss 결과가 올바르지 못하면 예외가 발생한다.")
    void authenticateWithNotValidRssResponse(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

    @Test
    @DisplayName("인증 코드 인증 요청 시 Rss 결과로 제목과 캐시의 인증코드가 일치하지 않으면 예외가 발생한다.")
    void authenticateWithNotValidRssTitle(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }
}