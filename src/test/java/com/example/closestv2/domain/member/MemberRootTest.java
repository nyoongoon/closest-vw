package com.example.closestv2.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRootTest {
    private final String ANY_USER_EMAIL = "abc@naver.com";
    private final String ANY_PASSWORD = "Abc1234!!";
    private final String ANY_NICK_NAME = "닉네임";
    private final URL ANY_URL = URI.create("https://goalinnext.tistory.com/rss").toURL();

    MemberRootTest() throws MalformedURLException {
    }

    @DisplayName("Member 루트의 myBlog가 존재하지 않으면 hasMyBlog()가 false를 리턴한다.")
    @Test
    void hasMyBlogWithoutMyBlog() {
        //given
        MemberRoot memberRoot = MemberRoot.create(ANY_USER_EMAIL, ANY_PASSWORD, ANY_NICK_NAME);
        //expected
        assertThat(memberRoot.getMyBlog()).isNull(); //myBlog가 null;
        assertThat(memberRoot.hasMyBlog()).isFalse();
    }

    @DisplayName("Member 루트의 myBlog가 존재하고 url값이 존재하면 hasMyBlog()가 true를 리턴한다.")
    @Test
    void hasMyBlogWithMyBlog() {
        //given
        MemberRoot memberRoot = MemberRoot.create(ANY_USER_EMAIL, ANY_PASSWORD, ANY_NICK_NAME);
        //when
        memberRoot.saveMyBlog(ANY_URL, 0L);
        //then
        assertThat(memberRoot.hasMyBlog()).isTrue();
    }

    @DisplayName("Member는 myBlog의 상태메시지를 변경할 수 있다.")
    @Test
    void memberRootWithStatusMessage() {
        //given
        MemberRoot memberRoot = MemberRoot.create(ANY_USER_EMAIL, ANY_PASSWORD, ANY_NICK_NAME);
        memberRoot.saveMyBlog(ANY_URL, 0L);
        String statusMessage = "상태 메시지입니다."; //상태 메시지
        //when
        memberRoot.withStatusMessage(statusMessage);
        //then
        assertThat(memberRoot.getMyBlog().getStatusMessage()).isEqualTo("상태 메시지입니다.");
    }

    @Test
    @DisplayName("MyBlog 생성 시 myBlogVisitCount는 외부에서 전달받아 생성된다.")
    void createMyBlogWithMyBlogVisitCount() {
        //given
        Long blogVisitCount = 11L; //TODO 해당 테스트 따로 만들기 - 기존에 url에 해당하는 BlogRoot가 존재하면 해당 블로그의 visitCount를 가져오고 존재하지 않으면 0L으로 생성
        MemberRoot memberRoot = MemberRoot.create(ANY_USER_EMAIL, ANY_PASSWORD, ANY_NICK_NAME);
        memberRoot.saveMyBlog(ANY_URL, blogVisitCount);
        //then
        assertThat(memberRoot.getMyBlog().getMyBlogVisitCount()).isEqualTo(blogVisitCount);
    }

    @Test
    @DisplayName("MyBlog 가 방문될 경우 visitMyBlog()로 방문횟수를 1 증가시킨다.")
    void visitMyBlog() {
        //given
        Long blogVisitCount = 11L; //TODO 해당 테스트 따로 만들기 - 기존에 url에 해당하는 BlogRoot가 존재하면 해당 블로그의 visitCount를 가져오고 존재하지 않으면 0L으로 생성
        MemberRoot memberRoot = MemberRoot.create(ANY_USER_EMAIL, ANY_PASSWORD, ANY_NICK_NAME);
        memberRoot.saveMyBlog(ANY_URL, blogVisitCount);
        //when
        memberRoot.visitMyBlog();
        //then
        assertThat(memberRoot.getMyBlog().getMyBlogVisitCount())
                .isEqualTo(blogVisitCount + 1);
    }
}