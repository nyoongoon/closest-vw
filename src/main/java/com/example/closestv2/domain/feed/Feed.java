package com.example.closestv2.domain.feed;

import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.example.closestv2.util.constant.SpecificDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Feed {
    private URL rssUrl;
    private URL blogUrl;
    private String blogTitle;
    private String author;
    private LocalDateTime publishedDateTime;
    private List<FeedItem> feedItems;

    public static Feed create(
            URL rssUrl,
            URL blogUrl,
            String blogTitle,
            String author,
            List<FeedItem> feedItems
    ) {
        //publishedDateTime 초기값은 에포크 타임
        LocalDateTime publishedDateTime = SpecificDate.EPOCH_TIME.getLocalDateTime();

        if (CollectionUtils.isEmpty(feedItems)) {
            feedItems = new ArrayList<>();
        } else {
            publishedDateTime = extractRecentPublishedDateTime(feedItems);
        }

        return Feed.builder()
                .rssUrl(rssUrl)
                .blogUrl(blogUrl)
                .blogTitle(blogTitle)
                .author(author)
                .publishedDateTime(publishedDateTime)
                .feedItems(feedItems)
                .build();
    }

    private static LocalDateTime extractRecentPublishedDateTime(List<FeedItem> feedItems) {
        LocalDateTime recentPublishedDateTime = SpecificDate.EPOCH_TIME.getLocalDateTime();
        for (FeedItem feedItem : feedItems) {
            LocalDateTime publishedDateTime = feedItem.getPublishedDateTime();
            if(recentPublishedDateTime.isBefore(publishedDateTime)){
                recentPublishedDateTime = publishedDateTime;
            }
        }
        return recentPublishedDateTime;
    }

    public BlogRoot toBlogRoot() {
        BlogRoot blogRoot = BlogRoot.create(
                rssUrl,
                blogUrl,
                blogTitle,
                author,
                publishedDateTime
        );
        Map<URL, Post> posts = blogRoot.getPosts();
        for (FeedItem feedItem : feedItems) {
            Post post = blogRoot.createPost(
                    feedItem.getPostUrl(),
                    feedItem.getPostTitle(),
                    feedItem.getPublishedDateTime()
            );
            posts.put(post.getPostUrl(), post);
        }
        return blogRoot;
    }
}
