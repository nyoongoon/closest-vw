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

import static com.example.closestv2.api.exception.ExceptionMessageConstants.CHANGED_STATUS_MESSAGE_IS_NULL;

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

    @Getter(value = AccessLevel.PROTECTED)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "blog_id")
    private List<Post> posts = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private BlogRoot(
            URL blogUrl,
            String blogTitle,
            String author,
            LocalDateTime publishedDateTime
    ) {
        blogInfo = BlogInfo.builder()
                .blogUrl(blogUrl)
                .blogTitle(blogTitle)
                .author(author)
                .publishedDateTime(publishedDateTime)
                .blogVisitCount(0L)
                .build();
    }

    public static BlogRoot create(
            URL url,
            String blogTitle,
            String author,
            LocalDateTime publishedDateTime
    ) {
        return BlogRoot.builder()
                .blogUrl(url)
                .blogTitle(blogTitle)
                .author(author)
                .publishedDateTime(publishedDateTime)
                .build();
    }

    public Post createPost (
            URL postUrl,
            String postTitle,
            LocalDateTime publishedDateTime
    ){
        return Post.builder()
                .postUrl(postUrl)
                .postTitle(postTitle)
                .publishedDateTime(publishedDateTime)
                .build();
    }

    public void withStatusMessage(String statusMessage){
        if(statusMessage == null){
            throw new IllegalStateException(CHANGED_STATUS_MESSAGE_IS_NULL);
        }
        blogInfo = BlogInfo.builder()
                .blogUrl(blogInfo.blogUrl())
                .blogTitle(blogInfo.blogTitle())
                .author(blogInfo.author())
                .publishedDateTime(blogInfo.publishedDateTime())
                .statusMessage(statusMessage)
                .build();
    }
}
