package com.example.closestv2.domain.blog;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.BLOG_NON_UPDATABLE_BY_PAST_PUBLISHED_DATETIME;
import static com.example.closestv2.api.exception.ExceptionMessageConstants.BLOG_UPDATABLE_BY_SAME_URL;

@Getter
@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogRoot {
    private static final Logger log = LoggerFactory.getLogger(BlogRoot.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Valid
    @Embedded
    private BlogInfo blogInfo;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "blog_id")
    private List<Post> posts = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private BlogRoot(
            BlogInfo blogInfo
    ) {
        this.blogInfo = blogInfo;
    }

    //todo 이게 여기 있는게 맞나? BlogFactory에 있어야할거같음..
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
        PostInfo postInfo = PostInfo.builder()
                .postUrl(postUrl)
                .postTitle(postTitle)
                .publishedDateTime(publishedDateTime)
                .postVisitCount(0L)
                .build();
        Post post = Post.builder()
                .postInfo(postInfo)
                .build();
        return post;
    }

    public void withStatusMessage(String statusMessage) {
        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.blogUrl())
                .blogTitle(blogInfo.blogTitle())
                .author(blogInfo.author())
                .publishedDateTime(blogInfo.publishedDateTime())
                .statusMessage(statusMessage)
                .build();
    }

    public boolean isBlogUpdated(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;

        if (blogInfo.blogTitle() != comparedBlogInfo.blogTitle()) {
            return true;
        }
        if (blogInfo.author() != comparedBlogInfo.author()) {
            return true;
        }
        if (blogInfo.publishedDateTime().isBefore(comparedBlogInfo.publishedDateTime())) {
            return true;
        }

        return false;
    }

    public void updateBlog(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;
        checkValidUpdate(comparedBlogInfo);

        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.blogUrl())
                .blogTitle(comparedBlogInfo.blogTitle())
                .author(comparedBlogInfo.author())
                .publishedDateTime(comparedBlogInfo.publishedDateTime())
                .blogVisitCount(blogInfo.blogVisitCount())
                .statusMessage(blogInfo.statusMessage())
                .build();
    }

    public boolean isPostsUpdated(
            BlogRoot comparedBlogRoot
    ) {
        BlogInfo comparedBlogInfo = comparedBlogRoot.blogInfo;
        checkValidUpdate(comparedBlogInfo);

        if (blogInfo.publishedDateTime().isBefore(comparedBlogInfo.publishedDateTime())) {
            return true;
        }

        return false;
    }

    public void updatePosts(
            BlogRoot comparedBlogRoot //feed로 받아온 블로그..
    ) {
        checkValidUpdate(comparedBlogRoot.blogInfo);
        LocalDateTime lastPublishedDateTime = LocalDateTime.MIN;

        List<Post> updatPosts = comparedBlogRoot.getPosts();
        for (Post updatePost : updatPosts) {
            LocalDateTime updatePostPublishedDateTime = updatePost.getPostInfo().publishedDateTime();
            if (lastPublishedDateTime.isBefore(updatePostPublishedDateTime)) {
                lastPublishedDateTime = updatePostPublishedDateTime;
            }
            posts.add(updatePost);
        }

        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.blogUrl())
                .blogTitle(blogInfo.blogTitle())
                .author(blogInfo.author())
                .publishedDateTime(lastPublishedDateTime)
                .blogVisitCount(blogInfo.blogVisitCount())
                .statusMessage(blogInfo.statusMessage())
                .build();
    }

    private void checkValidUpdate(BlogInfo comparedBlogInfo) {
        if (!blogInfo.blogUrl().equals(comparedBlogInfo.blogUrl())) {
            log.error("URL이 다른 블로그 정보로 업데이트 시도 - 기존: {}, 업데이트 시도: {}", blogInfo.blogUrl(), comparedBlogInfo.blogUrl());
            throw new IllegalStateException(BLOG_UPDATABLE_BY_SAME_URL);
        }
        // blogInfo의 발행시간이 더 이후면 예외
        if (blogInfo.publishedDateTime().isAfter(comparedBlogInfo.publishedDateTime())) {
            log.error("블로그 업데이트는 발행시간이 더 과거인 블로그로 업데이트 할 수 없다. 기존:{}, 업데이트 시도:{} ", blogInfo.publishedDateTime(), comparedBlogInfo.publishedDateTime());
            throw new IllegalStateException(BLOG_NON_UPDATABLE_BY_PAST_PUBLISHED_DATETIME);
        }
    }
}
