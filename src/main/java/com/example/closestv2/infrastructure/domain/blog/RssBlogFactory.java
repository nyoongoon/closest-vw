package com.example.closestv2.infrastructure.domain.blog;

import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RssBlogFactory implements BlogFactory {

    @Override
    public BlogRoot createRecentBlogRoot(URL rssUrl, SyndFeed syndFeed) throws MalformedURLException {
        BlogRoot blogRoot = translate(rssUrl, syndFeed);

        if (CollectionUtils.isEmpty(syndFeed.getEntries())) {
            return blogRoot;
        }

        LocalDateTime recentPublishedDateTime = blogRoot.getBlogInfo().getPublishedDateTime();
        Map<URL, Post> posts = blogRoot.getPosts();
        for (SyndEntry entry : syndFeed.getEntries()) {
            URL postUrl = URI.create(entry.getLink()).toURL();
            String postTitle = entry.getTitle();
            LocalDateTime publishedDate = toLocalDateTime(entry.getPublishedDate());
            Post post = blogRoot.createPost(
                    postUrl,
                    postTitle,
                    publishedDate
            );

            LocalDateTime updatePostPublishedDateTime = post.getPublishedDateTime();

            if (Objects.isNull(recentPublishedDateTime) || recentPublishedDateTime.isBefore(updatePostPublishedDateTime)) {
                recentPublishedDateTime = updatePostPublishedDateTime;
            }
            posts.put(post.getPostUrl(), post);
        }
        blogRoot.updatePublishedDateTime(recentPublishedDateTime);

        return blogRoot;
    }

    private BlogRoot translate(URL rssUrl, SyndFeed syndFeed) throws MalformedURLException {
        URL blogUrl = URI.create(syndFeed.getLink()).toURL();
        String blogTitle = syndFeed.getTitle();
        String author = syndFeed.getAuthor();
        return BlogRoot.create(
                rssUrl,
                blogUrl,
                blogTitle,
                author
        );
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
