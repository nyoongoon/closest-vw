package com.example.closestv2.domain.member.model;

import com.example.closestv2.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
//
//    @DisplayName("Member 루트 생성 시 이메일, 패스워드, 닉네임은 필수값이다.")
//    @Test
//    void createMember(){
//
//    }
//
//    @DisplayName("Member 루트의 userEmail 형식이 이메일이 아니면 생성 시 에러가 발생한다.")
//    @Test
//    void createMemberByNotValidEmail() {
//        //given
////        Member member = Member.create(
////                "abc@naver.com",
////                        "1234"
////
////                );
//
//
//        //when
//
//        //then
//
//    }

    @DisplayName("Member 루트의 나의 블로그 url값이 존재하지않으면 hasMyBlog()가 false를 리턴한다.")
    @Test
    void hasMyBlogWithoutMyBlog() {
        //given
        String userEmail = "";
        String password = "";
        String nickName = "";

        //when
        Member member = Member.create(
                userEmail,
                password,
                nickName
        );

        //then
        assertThat(member.hasBlog()).isTrue();
    }
    @DisplayName("Member 루트의 나의 블로그 url값이 존재하면 hasMyBlog()가 true를 리턴한다.")
    @Test
    void hasMyBlogWithMyBlog() {
        //given
        String userEmail = "";
        String password = "";
        String nickName = "";

        //when
        Member member = Member.create(
                userEmail,
                password,
                nickName
        );

        //then
        assertThat(member.hasBlog()).isFalse();
    }
}