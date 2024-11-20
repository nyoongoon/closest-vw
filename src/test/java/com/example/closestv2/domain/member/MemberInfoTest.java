package com.example.closestv2.domain.member;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class MemberInfoTest extends RepositoryTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 생성 시 MemberInfo의 이메일, 비밀번호, 닉네임이 있어야한다.")
    @Test
    void createMemberByValidMemberInfoValue() {
        //given
        String validEmail = "abc@naver.com";
        String validPassword = "Abc1234!!";
        String nickName = "닉네임";
        MemberRoot memberRoot = MemberRoot.create(
                validEmail,
                validPassword,
                nickName
        );
        //when
        memberRepository.save(memberRoot);
        //then
        assertThat(memberRoot)
                .extracting(e -> e.getMemberInfo().userEmail(), e -> e.getMemberInfo().password(), e -> e.getMemberInfo().nickName())
                .containsExactly("abc@naver.com", "Abc1234!!", "닉네임");
    }

    @DisplayName("Member 생성 시 MemberInfo의 이메일 값이 이메일 형식이 아니면 에러가 발생한다.")
    @Test
    void createMemberByNotValidEmail() {
        //given
        String validEmail = "@naver.com";
        String validPassword = "Abc1234!!";
        String nickName = "닉네임";
        //when
        MemberRoot memberRoot = MemberRoot.create(
                validEmail,
                validPassword,
                nickName
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 이메일 값이 null이면 에러가 발생한다.")
    @Test
    void createMemberByNullEmail() {
        //given
        String notValidEmail = null;
        //when
        MemberRoot memberRoot = MemberRoot.create(
                notValidEmail,
                "Abc1234!",
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 이메일 값이 공백이면 에러가 발생한다.")
    @Test
    void createMemberByBlankEmail() {
        //given
        String blankEmail = " ";
        //when
        MemberRoot memberRoot = MemberRoot.create(
                blankEmail,
                "Abc1234!",
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 null이면 에러가 발생한다.")
    @Test
    void createMemberByNullPassword() {
        //given
        String notValidPassword = null;
        //when
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                notValidPassword,
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 공백이면 에러가 발생한다.")
    @Test
    void createMemberByBlankPassword() {
        //given
        String blankPassword = " ";
        //when
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                blankPassword,
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 대소문자숫자특수문자 하나 이상 포함한 형식이 아니면 에러가 발생한다.")
    @Test
    void createMemberByNotValidPassword() {
        //given
        String notValidPassword = "abc1234!"; // 대문자 없음
        //expected
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                notValidPassword,
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 비밀번호 값이 8자 이하면 에러가 발생한다.")
    @Test
    void createMemberByPasswordUnder8Length() {
        //given
        String passwordUnder8Length = "Ac14!"; // 8자 이하
        //when
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                passwordUnder8Length,
                "닉네임"
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 닉네임이 null이면 에러가 발생한다.")
    @Test
    void createMemberByNullNickName() {
        //given
        String nickName = null;
        //when
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                "Ab1234!!",
                nickName
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }

    @DisplayName("Member 생성 시 MemberInfo의 닉네임이 공백이면 에러가 발생한다.")
    @Test
    void createMemberByBlankNickname() {
        //given
        String nickName = " "; //blank
        //when
        MemberRoot memberRoot = MemberRoot.create(
                "abc@Email.com",
                "Ab1234!!",
                nickName
        );
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
        throw new IllegalArgumentException("일원화하기");
    }
}