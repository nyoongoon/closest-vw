package com.example.closestv2.domain.blog;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
