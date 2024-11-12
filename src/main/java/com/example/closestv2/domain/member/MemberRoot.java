package com.example.closestv2.domain.member;

import com.example.closestv2.domain.Events;
import com.example.closestv2.domain.member.event.StatusMessageChangeEvent;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.ALREADY_EXISTS_MY_BLOG;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.NOT_EXISTS_MY_BLOG;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @Embedded
    private MemberInfo memberInfo;

    @Embedded
    private MyBlog myBlog;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberRoot(
            String userEmail,
            String password,
            String nickName
    ) {
        this.memberInfo = MemberInfo.builder()
                .userEmail(userEmail)
                .password(password)
                .nickName(nickName)
                .build();
    }

    public static MemberRoot create(
            String userEmail,
            String password,
            String nickName
    ) {
        return MemberRoot.builder()
                .userEmail(userEmail)
                .password(password)
                .nickName(nickName)
                .build();
    }

    public boolean hasMyBlog() {
        if (ObjectUtils.isEmpty(myBlog)) {
            return false;
        }
        URL url = myBlog.url();
        if (ObjectUtils.isEmpty(url)) { //url이 존재하면 나의 블로그 존재
            return false;
        }
        return true;
    }

    public void addMyBlog(
            URL url
    ) {
        if (hasMyBlog()) {
            throw new IllegalStateException(ALREADY_EXISTS_MY_BLOG); //블로그 변경 시 변경 메서드 사용
        }

        myBlog = MyBlog.builder()
                .url(url)
                .build();
    }

    // 이벤트 발생
    public void withStatusMessage(String statusMessage) {
        if (!hasMyBlog()) {
            throw new IllegalStateException(NOT_EXISTS_MY_BLOG);
        }
        URL url = myBlog.url();
        myBlog = MyBlog.builder()
                .url(url)
                .statusMessage(statusMessage)
                .build();

        //todo 블로그 도메인에서 이벤트 받아 처리..
        Events.raise(new StatusMessageChangeEvent(url, statusMessage));
    }
}
