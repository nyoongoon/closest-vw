package com.example.closestv2.domain.blog;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.BLOG_NON_UPDATABLE_BY_PAST_PUBLISHED_DATETIME;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.BLOG_UPDATABLE_BY_SAME_URL;

@Slf4j
@Getter
@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Valid
    @Embedded
    private BlogInfo blogInfo;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="post", joinColumns = @JoinColumn(name="blog_id"))
//    @OrderColumn(name="post_id") // 꼭 필요한지?
    private List<Post> posts = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private BlogRoot(
            BlogInfo blogInfo
    ) {
        this.blogInfo = blogInfo;
    }

    public static BlogRoot create(
            URL blogUrl,
            String blogTitle,
            String author,
            LocalDateTime publishedDateTime
    ) {
        BlogInfo blogInfo = BlogInfo.builder()
                .blogUrl(blogUrl)
                .blogTitle(blogTitle)
                .author(author)
                .publishedDateTime(publishedDateTime)
                .blogVisitCount(0L)
                .build();
        return BlogRoot.builder()
                .blogInfo(blogInfo)
                .build();
    }

    public Post createPost(
            URL postUrl,
            String postTitle,
            LocalDateTime publishedDateTime
    ) {
        Post post = Post.builder()
                .postUrl(postUrl)
                .postTitle(postTitle)
                .publishedDateTime(publishedDateTime)
                .postVisitCount(0L)
                .build();
        return post;
    }

    public void editStatusMessage(String statusMessage) {
        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.getBlogUrl())
                .blogTitle(blogInfo.getBlogTitle())
                .author(blogInfo.getAuthor())
                .publishedDateTime(blogInfo.getPublishedDateTime())
                .blogVisitCount(blogInfo.getBlogVisitCount())
                .statusMessage(statusMessage)
                .build();
    }

    public boolean isBlogUpdated(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;

        if (blogInfo.getBlogTitle() != comparedBlogInfo.getBlogTitle()) {
            return true;
        }
        if (blogInfo.getAuthor() != comparedBlogInfo.getAuthor()) {
            return true;
        }
        if (blogInfo.getPublishedDateTime().isBefore(comparedBlogInfo.getPublishedDateTime())) {
            return true;
        }

        return false;
    }

    public void updateBlogRoot(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;
        checkValidUpdate(comparedBlogInfo);

        boolean isPostUpdated = isPostsUpdated(comparedBlogRoot);

        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.getBlogUrl())
                .blogTitle(comparedBlogInfo.getBlogTitle())
                .author(comparedBlogInfo.getAuthor())
                .publishedDateTime(comparedBlogInfo.getPublishedDateTime())
                .blogVisitCount(blogInfo.getBlogVisitCount())
                .statusMessage(blogInfo.getStatusMessage())
                .build();

        // 포스트 업데이트 여부는 블로그 발행시간을 비교하므로, 메서드 호출 시 미리 변경할 대상의 블로그 발행시간을 업데이트하면 안된다.
        if(isPostUpdated){
            updatePosts(comparedBlogRoot);
        }
    }

    /**
     * 블로그 발행시간을 비교하므로, 메서드 호출 시 미리 변경할 대상의 블로그 발행시간을 업데이트하면 안된다.
     */
    private boolean isPostsUpdated(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;
        checkValidUpdate(comparedBlogInfo);

        if (blogInfo.getPublishedDateTime().isBefore(comparedBlogInfo.getPublishedDateTime())) {
            return true;
        }

        return false;
    }

    private void updatePosts(
            BlogRoot comparedBlogRoot
    ) {
        checkValidUpdate(comparedBlogRoot.blogInfo);

        List<Post> updatPosts = comparedBlogRoot.getPosts();
        for (Post updatePost : updatPosts) {
            // 기존 포스트와 URL 중복되면 안됨 //TODO post는 엔티티이므로 식별자로 동등성하는가?
            if(!posts.contains(updatePost)){
                posts.add(updatePost);
            }
        }
    }

    private void checkValidUpdate(BlogInfo comparedBlogInfo) {
        if (!blogInfo.getBlogUrl().equals(comparedBlogInfo.getBlogUrl())) {
            log.error("URL이 다른 블로그 정보로 업데이트 시도 - 기존: {}, 업데이트 시도: {}", blogInfo.getBlogUrl(), comparedBlogInfo.getBlogUrl());
            throw new IllegalStateException(BLOG_UPDATABLE_BY_SAME_URL);
        }
        // blogInfo의 발행시간이 더 이후면 예외
        if (blogInfo.getPublishedDateTime().isAfter(comparedBlogInfo.getPublishedDateTime())) {
            log.error("블로그 업데이트는 발행시간이 더 과거인 블로그로 업데이트 할 수 없다. 기존:{}, 업데이트 시도:{} ", blogInfo.getPublishedDateTime(), comparedBlogInfo.getPublishedDateTime());
            throw new IllegalStateException(BLOG_NON_UPDATABLE_BY_PAST_PUBLISHED_DATETIME);
        }
    }
}
