package com.example.closestv2.domain.likes;

import com.example.closestv2.support.RepositoryTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class LikesRootTest extends RepositoryTestSupport {

    @Autowired
    private LikesRepository likesRepository;

    @DisplayName("Like 생성 시 memberId, postUrl이 필요하다.")
    @Test
    void create() throws MalformedURLException {
        //given
        Long memberId = 1L;
        URL postUrl = new URL("https://example.com/blog123/post/123");
        LikesRoot likesRoot = LikesRoot.create(
                memberId,
                postUrl
        );
        //when
        likesRepository.save(likesRoot);

        //then
        assertThat(likesRoot.getId())
                .isNotNull();
    }
}