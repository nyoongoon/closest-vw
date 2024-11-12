package com.example.closestv2.domain.member;

import com.example.closestv2.support.RepositoryTestSupport;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MyBlogTest extends RepositoryTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Member가 MyBlog를 추가할 때 Url값이 null이면 예외가 발생한다.")
    @Test
    void memberRootWithNullUrl() {
        //given
        String userEmail = "abc@naver.com";
        String password = "Ab1234!!";
        String nickName = null;
        MemberRoot memberRoot = MemberRoot.create(
                userEmail,
                password,
                nickName
        );
        URL url = null;
        memberRoot.addMyBlog(url);
        //expected
        assertThatThrownBy(() -> memberRepository.save(memberRoot))
                .isInstanceOf(ConstraintViolationException.class);
    }
}