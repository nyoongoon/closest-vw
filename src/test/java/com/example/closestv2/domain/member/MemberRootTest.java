package com.example.closestv2.domain.member;

import com.example.closestv2.support.RepositoryTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRootTest extends RepositoryTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member 루트의 myBlog가 존재하지 않으면 hasMyBlog()가 false를 리턴한다.")
    @Test
    void hasMyBlogWithoutMyBlog() {
        //given
        String userEmail = "abc@naver.com";
        String password = "Abc1234!!";
        String nickName = "닉네임";

        //when
        MemberRoot memberRoot = MemberRoot.create(
                userEmail,
                password,
                nickName
        );
        memberRepository.save(memberRoot);
        //then
        assertThat(memberRoot.getMyBlog()).isNull(); //myBlog가 null;
        assertThat(memberRoot.hasMyBlog()).isFalse();
    }

    @DisplayName("Member 루트의 myBlog가 존재하고 url값이 존재하면 hasMyBlog()가 true를 리턴한다.")
    @Test
    void hasMyBlogWithMyBlog() throws MalformedURLException {
        //given
        String userEmail = "abc@naver.com";
        String password = "Ab1234!!";
        String nickName = null;
        MemberRoot memberRoot = MemberRoot.create(
                userEmail,
                password,
                nickName
        );
        URL url = new URL("https://goalinnext.tistory.com/rss");
        //when
        memberRoot.addMyBlog(url);
        //then
        assertThat(memberRoot.hasMyBlog()).isTrue();
    }
}