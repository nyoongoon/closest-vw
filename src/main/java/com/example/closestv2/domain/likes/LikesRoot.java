package com.example.closestv2.domain.likes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.net.URL;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.MEMBER_ID_IS_REQUIRED;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.POST_URL_IS_REQUIRED;

@Getter
@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @NotNull(message = MEMBER_ID_IS_REQUIRED)
    private Long memberId;

    @NotNull(message = POST_URL_IS_REQUIRED)
    private URL postUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private LikesRoot(Long memberId, URL postUrl) {
        Assert.notNull(memberId, MEMBER_ID_IS_REQUIRED);
        Assert.notNull(postUrl, POST_URL_IS_REQUIRED);
        this.memberId = memberId;
        this.postUrl = postUrl;
    }

    public static LikesRoot create(
            Long memberId,
            URL postUrl
    ) {
        return LikesRoot.builder()
                .memberId(memberId)
                .postUrl(postUrl)
                .build();
    }
}
