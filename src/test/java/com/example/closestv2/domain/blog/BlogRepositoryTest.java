package com.example.closestv2.domain.blog;

import com.example.closestv2.support.RepositoryTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class BlogRepositoryTest extends RepositoryTestSupport {
    private final URL ANY_RSS_URL = URI.create("http://example.com/rss").toURL();
    private final URL ANY_BLOG_URL = URI.create("https://example.com/").toURL();
    private final String ANY_TITLE = "제목";
    private final String ANY_AUTHOR = "작가";

    @Autowired
    private BlogRepository blogRepository;

    BlogRepositoryTest() throws MalformedURLException {
    }

    @Test
    @DisplayName("URL타입 blogUrl로 BlogRoot를 조회한다. ")
    void findByBlogUrl() {
        //given
        blogRepository.save(BlogRoot.create(ANY_RSS_URL, ANY_BLOG_URL, ANY_TITLE, ANY_AUTHOR));
        //when
        BlogRoot sut = blogRepository.findByBlogInfoBlogUrl(ANY_BLOG_URL).orElseThrow();
        //then
        BlogInfo blogInfo = sut.getBlogInfo();
        assertThat(blogInfo)
                .extracting(BlogInfo::getRssUrl, BlogInfo::getBlogUrl, BlogInfo::getBlogTitle, BlogInfo::getAuthor)
                .containsExactly(ANY_RSS_URL, ANY_BLOG_URL, ANY_TITLE, ANY_AUTHOR);
    }
}