package com.example.closestv2.domain.member;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @Embedded
    private MemberInfo memberInfo;

    @Embedded
    private MyBlog myBlog;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(
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

    public static Member create(
            String userEmail,
            String password,
            String nickName
    ) {
        return Member.builder()
                .userEmail(userEmail)
                .password(password)
                .nickName(nickName)
                .build();
    }

    public boolean hasBlog() {
        String url = myBlog.getUrl();
        if (StringUtils.isBlank(url)) { //url이 존재하면 나의 블로그 존재
            return false;
        }
        return true;
    }
}
