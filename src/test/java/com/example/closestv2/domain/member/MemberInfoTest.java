package com.example.closestv2.domain.member;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberInfoTest extends RepositoryTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 생성 시 MemberInfo의 이메일 값이 이메일 형식이 아니면 에러가 발생한다.")
    @Test
    void createMemberByValidMemberInfoValue() {
        //given
        String validEmail = "abc@naver.com";
        String validPassword = "Abc1234!!";
        String nickName = "별명";
        //when
        Member member = Member.create(
                validEmail,
                validPassword,
                nickName
        );
        //then
        memberRepository.save(member);
    }

    @DisplayName("Member 생성 시 MemberInfo의 이메일 값이 이메일 형식이 아니면 에러가 발생한다.")
    @Test
    void createMemberByNotValidEmail() {
        //given
        String notValidEmail = "abc";
        //when
        Member member = Member.create(
                notValidEmail,
                "Abc1234!",
                "별명"
        );
        //then
        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 대소문자숫자특수문자 하나 이상 포함한 형식이 아니면 에러가 발생한다.")
    @Test
    void createMemberByNotValidPassword() {
        //given
        String notValidPassword = "abc1234!"; // 대문자 없음
        //when
        Member member = Member.create(
                "abc@Email.com",
                notValidPassword,
                "별명"
        );
        //then
        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 8자 이하면 에러가 발생한다.")
    @Test
    void createMemberByPasswordUnder8Length() {
        //given
        String passwordUnder8Length = "Ac14!"; // 8자 이하
        //when
        Member member = Member.create(
                "abc@Email.com",
                passwordUnder8Length,
                "별명"
        );
        //then
        assertThatThrownBy(() -> memberRepository.save(member))
                .isInstanceOf(ConstraintViolationException.class);
    }
}