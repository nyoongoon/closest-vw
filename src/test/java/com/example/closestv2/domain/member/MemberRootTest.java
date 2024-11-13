package com.example.closestv2.domain.member;

import com.example.closestv2.support.RepositoryTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
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
        memberRoot.saveMyBlog(url, 0L);
        //then
        assertThat(memberRoot.hasMyBlog()).isTrue();
    }

    @DisplayName("Member는 myBlog의 상태메시지를 변경할 수 있다.")
    @Test
    void memberRootWithStatusMessage() throws MalformedURLException {
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
        memberRoot.saveMyBlog(url, 0L);
        //상태 메시지
        String statusMessage = "상태 메시지입니다.";
        //when
        memberRoot.withStatusMessage(statusMessage);
        //then
        assertThat(memberRoot.getMyBlog().statusMessage()).isEqualTo("상태 메시지입니다.");
    }

    @Test
    @DisplayName("MyBlog 생성 시 myBlogVisitCount는 외부에서 전달받아 생성된다.")
    void createMyBlogWithMyBlogVisitCount() throws MalformedURLException {
        //given
        Long blogVisitCount = 11L; // 기존에 blog가 존재하면 해당 블로그의 visitCount를 가져오고 존재하지 않으면 0L으로 생성
        MemberRoot memberRoot = MemberRoot.create(
                "abc@naver.com",
                "Ab1234!!",
                "닉네임"
        );
        //when
        memberRoot.saveMyBlog(new URL("https://goalinnext.tistory.com/rss"), blogVisitCount);
        //then
        assertThat(memberRoot.getMyBlog().myBlogVisitCount())
                .isEqualTo(11L);
    }

    @Test
    @DisplayName("MyBlog 가 방문될 경우 visitMyBlog()로 방문횟수를 1 증가시킨다.")
    void visitMyBlog() throws MalformedURLException {
        //given
        Long blogVisitCount = 11L; // 기존에 blog가 존재하면 해당 블로그의 visitCount를 가져오고 존재하지 않으면 0L으로 생성
        MemberRoot memberRoot = MemberRoot.create(
                "abc@naver.com",
                "Ab1234!!",
                "닉네임"
        );
        //when
        memberRoot.saveMyBlog(new URL("https://goalinnext.tistory.com/rss"), blogVisitCount);
        memberRoot.visitMyBlog();
        //then
        assertThat(memberRoot.getMyBlog().myBlogVisitCount())
                .isEqualTo(12L);
    }
}