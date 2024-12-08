package com.example.closestv2.infrastructure.domain.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlogAuthCodeRepositoryTest {
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
    @DisplayName("인증 코드 인증 요청 시 캐시에 존재하지 않는 memberId인 경우 예외가 발생한다.")
    void authenticateWithNotExistsdMemberIdInCache(){
        //given
        //when
        //then
        throw new IllegalStateException();
    }

}