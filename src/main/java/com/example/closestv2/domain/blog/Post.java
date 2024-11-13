package com.example.closestv2.domain.blog;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Valid
    @Embedded
    private PostInfo postInfo;

    @Builder(access = AccessLevel.PROTECTED)
    private Post(
            URL postUrl,
            String postTitle,
            LocalDateTime publishedDateTime
    ) {
        postInfo = PostInfo.builder()
                .postUrl(postUrl)
                .postTitle(postTitle)
                .publishedDateTime(publishedDateTime)
                .postVisitCount(0L)
                .build();
    }
}
